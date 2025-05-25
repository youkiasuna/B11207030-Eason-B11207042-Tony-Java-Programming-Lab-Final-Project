import javax.swing.JOptionPane;

public class GameManager {
    private MainFrame frame;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;

    public GameManager(MainFrame frame) {
        this.frame = frame;
        menuPanel = new MenuPanel(this);
    }

    public void startGame(int level) {
        gamePanel = new GamePanel(this, level);
        frame.setContentPane(gamePanel);
        frame.revalidate();
        gamePanel.requestFocusInWindow(); // 確保 GamePanel 獲得焦點
    }

    public void startNextLevel(int currentLevel) {
         if (currentLevel >= LevelLoader.MAX_LEVEL) {
            // 最後一關通關，顯示完成訊息
            JOptionPane.showMessageDialog(
                frame,
                "🎉 恭喜你完成所有關卡！",
                "遊戲完成",
                JOptionPane.INFORMATION_MESSAGE
            );
            returnToMenu();
        } else {
            startGame(currentLevel + 1);
        }
    }

    public void returnToMenu() {
        frame.setContentPane(menuPanel);
        frame.revalidate();
        menuPanel.requestFocusInWindow(); // 確保 MenuPanel 獲得焦點
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}