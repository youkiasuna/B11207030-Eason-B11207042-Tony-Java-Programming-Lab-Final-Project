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
    private int level;
    private int bombsLeft;
    private boolean gameEnded = false;
    private GameManager gameManager;

    public GamePanel(GameManager manager, int level) {
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

        this.gameManager = manager;
        this.level = level;
        LevelConfig config = LevelLoader.loadLevel(level);
        this.minerals = config.minerals;
        this.mice = config.mice;
        this.scoreManager = new ScoreManager(config.targetScore);
        this.timerManager = new TimerManager(config.timeLimit);
        this.hook = new Hook(scoreManager);
        this.bombsLeft = config.bombCount;  // 直接初始化 bombCount

        backgroundGround = ImageLoader.loadImage("background_ground.jpg");
        backgroundUnderground = ImageLoader.loadImage("background_underground.jpg");
        minerImage = ImageLoader.loadImage("miner.png");

        timer = new javax.swing.Timer(20, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameEnded) return;
        
        hook.update(minerals, mice);
        
        for (Mouse mouse : mice) {
            if (!mouse.isCollected() && !mouse.isDestroyed()) {
                mouse.update();
            }
        }
        
        for (Mineral mineral : minerals) {
            if (!mineral.isCollected() && !mineral.isDestroyed()) {
                mineral.update();
            }
        }

        repaint();
        
        boolean allCollected = minerals.stream().allMatch(m -> m.isCollected() || m.isDestroyed()) &&
                            mice.stream().allMatch(m -> m.isCollected() || m.isDestroyed());
        if (allCollected && !hook.isReturning() && hook.getCaughtItem() == null) {
            gameEnded = true;
            System.out.println("Game ended. Score: " + scoreManager.getScore() + ", Target: " + scoreManager.getTargetScore());
            if (scoreManager.hasMetGoal()) {
                JOptionPane.showMessageDialog(this, "恭喜過關！", "遊戲結束", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Win condition met, returning to menu");
            } else {
                JOptionPane.showMessageDialog(this, "分數未達標，挑戰失敗", "遊戲結束", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Score not met, returning to menu");
            }
            gameManager.returnToMenu();
        } else if (allCollected) {
            // 添加日誌，追蹤為何不結束
            System.out.println("All items collected/destroyed, but game not ended. isReturning: " + hook.isReturning() + ", caughtItem: " + (hook.getCaughtItem() != null));
        }
        
        if (timerManager.isTimeUp() && !gameEnded) {
            gameEnded = true;
            if (scoreManager.hasMetGoal()) {
                JOptionPane.showMessageDialog(this, "時間到！恭喜達標", "遊戲結束", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "時間到！未達標", "遊戲結束", JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("Time up. Score: " + scoreManager.getScore() + ", Target: " + scoreManager.getTargetScore());
            gameManager.returnToMenu();
        }

        if (scoreManager.hasMetGoal() && !gameEnded) {
            gameEnded = true;
            JOptionPane.showMessageDialog(this, "恭喜達成目標分數！", "遊戲結束", JOptionPane.INFORMATION_MESSAGE);
            gameManager.returnToMenu();
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
        g.drawString("Target: " + scoreManager.getTargetScore(), 650, 30);
    }

    private class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameEnded) {
            hook.launch();
            } else if (e.getKeyCode() == KeyEvent.VK_C && !gameEnded) {
                if (bombsLeft> 0 && hook.getCaughtItem() != null) {
                    if (hook.useBomb()) {
                        bombsLeft--;
                        System.out.println("Bomb count reduced to: " + bombsLeft);
                    } else {
                        System.out.println("Failed to use bomb");
                    }
                } else {
                    System.out.println("No bombs left or no item to bomb");
                }
            }
        }
    }
}