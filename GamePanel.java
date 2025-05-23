import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    private javax.swing.Timer timer;
    private Hook hook;
    private ArrayList<Mineral> minerals;
    private ArrayList<Mouse> mice;
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
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("GamePanel lost focus");
                requestFocusInWindow();
            }
        });
        setLayout(null);
        requestFocusInWindow();

        LevelConfig config = LevelLoader.loadLevel(level);
        this.minerals = config.minerals;
        this.mice = config.mice;
        this.scoreManager = new ScoreManager(config.targetScore);
        this.timerManager = new TimerManager(config.timeLimit);
        this.hook = new Hook(scoreManager);
        this.bombsLeft = config.bombCount;

        backgroundGround = ImageLoader.loadImage("background_ground.jpg");
        backgroundUnderground = ImageLoader.loadImage("background_underground.jpg");
        minerImage = ImageLoader.loadImage("miner.png");

        timer = new javax.swing.Timer(20, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timerManager.update();
        hook.update(minerals, mice);
        if (hook.isReturning() && hook.getCaughtItem() != null) {
            // 不再需要 updateRotation（老鼠無旋轉）
        }
        for (Mineral m : minerals) {
            if (!m.isCollected()) {
                m.updateRotation();
            }
        }
        for (Mouse m : mice) {
            if (!m.isCollected()) {
                m.update();
            }
        }

        boolean allCollected = minerals.stream().allMatch(Mineral::isCollected) &&
                              mice.stream().allMatch(Mouse::isCollected);
        if (allCollected && !hook.isReturning() && scoreManager.hasMetGoal()) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "You win!");
            System.out.println("All items collected, hook returned, score met, game ended. Score: " + scoreManager.getScore() + ", Target: " + scoreManager.getTargetScore());
            manager.returnToMenu();
        } else if (timerManager.isTimeUp()) {
            timer.stop();
            JOptionPane.showMessageDialog(this, scoreManager.hasMetGoal() ? "You win!" : "You lose!");
            System.out.println("Time up, game ended. Score: " + scoreManager.getScore() + ", Target: " + scoreManager.getTargetScore());
            manager.returnToMenu();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundGround != null) {
            g.drawImage(backgroundGround, 0, 0, getWidth(), getHeight() / 5, this);
        }
        if (backgroundUnderground != null) {
            g.drawImage(backgroundUnderground, 0, getHeight() / 5, getWidth(), getHeight() * 4 / 5, this);
        }
        if (minerImage != null) {
            g.drawImage(minerImage, 400 - 40, 120 - 80, 80, 80, this);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(400 - 40, 120 - 80, 80, 80);
        }
        for (Mineral m : minerals) m.draw(g);
        for (Mouse m : mice) m.draw(g);
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
                hook.launch();
            }
            if (e.getKeyCode() == KeyEvent.VK_C && bombsLeft > 0) {
                if (hook.useBomb()) {
                    bombsLeft--;
                    System.out.println("Bomb used, bombs left: " + bombsLeft);
                }
            }
            requestFocusInWindow();
        }
    }
}