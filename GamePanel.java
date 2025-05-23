import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    private javax.swing.Timer timer;
    private Hook hook;
    private ArrayList<Mineral> minerals;
    private ScoreManager scoreManager;
    private TimerManager timerManager;
    private Image backgroundGround;
    private Image backgroundUnderground;
    private Image minerImage;
    private GameManager manager;
    private int level;
    private int bombsLeft;

    public GamePanel(GameManager manager, int level) {
        this.manager = manager;
        this.level = level;
        setFocusable(true);
        addKeyListener(new GameKeyAdapter());
        setLayout(null);
        requestFocusInWindow();

        LevelConfig config = LevelLoader.loadLevel(level);
        this.minerals = config.minerals;
        this.scoreManager = new ScoreManager(config.targetScore);
        this.timerManager = new TimerManager(config.timeLimit);
        this.hook = new Hook(scoreManager);
        this.bombsLeft = config.bombCount;

        backgroundGround = ImageLoader.loadImage("background_ground.jpg");
        backgroundUnderground = ImageLoader.loadImage("background_underground.jpg");
        minerImage = ImageLoader.loadImage("miner.png");

        if (backgroundGround.getWidth(null) <= 0) {
            System.err.println("Failed to load background_ground.jpg");
        }
        if (backgroundUnderground.getWidth(null) <= 0) {
            System.err.println("Failed to load background_underground.jpg");
        }
        if (minerImage.getWidth(null) <= 0) {
            System.err.println("Failed to load miner.png");
        }

        timer = new javax.swing.Timer(20, this);
        timer.start();
        System.out.println("GamePanel initialized, timer started");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timerManager.update();
        hook.update();
        if (hook.isReturning() && hook.getCaughtMineral() != null) {
            hook.getCaughtMineral().updateRotation();
        }
        for (Mineral m : minerals) {
            if (!m.isCollected()) {
                m.updateRotation();
            }
        }
        if (hook.isReturning()) hook.checkCollision(minerals, scoreManager);

        if (timerManager.isTimeUp()) {
            timer.stop();
            JOptionPane.showMessageDialog(this, scoreManager.hasMetGoal() ? "You win!" : "You lose!");
            manager.returnToMenu();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundGround, 0, 0, getWidth(), getHeight() / 5, this);
        g.drawImage(backgroundUnderground, 0, getHeight() / 5, getWidth(), getHeight() * 4 / 5, this);
        g.drawImage(minerImage, getWidth() / 2 - 40, getHeight() / 5 - 80, 80, 80, this);
        for (Mineral m : minerals) m.draw(g);
        hook.draw(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + scoreManager.getScore(), 20, 30);
        g.drawString("Time: " + timerManager.getTimeLeft(), 150, 30);
        g.drawString("Bombs: " + bombsLeft, 280, 30);
    }

    private class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key pressed: " + e.getKeyCode() + " (" + KeyEvent.getKeyText(e.getKeyCode()) + ")");
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                System.out.println("Space key pressed, calling hook.launch()");
                hook.launch();
            }
            if (e.getKeyCode() == KeyEvent.VK_C && bombsLeft > 0) {
                System.out.println("C key pressed, attempting to use bomb");
                if (hook.useBomb()) {
                    bombsLeft--;
                    System.out.println("Bomb used, bombs left: " + bombsLeft);
                } else {
                    System.out.println("Bomb use failed");
                }
            }
            requestFocusInWindow();
        }
    }
}