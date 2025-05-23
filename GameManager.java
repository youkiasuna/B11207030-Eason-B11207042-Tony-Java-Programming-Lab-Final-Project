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