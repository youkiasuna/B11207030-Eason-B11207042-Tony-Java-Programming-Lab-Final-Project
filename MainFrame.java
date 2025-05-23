import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Gold Miner Game");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GameManager gameManager = new GameManager(this);
        setContentPane(gameManager.getMenuPanel());
        setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Working Directory: " + System.getProperty("user.dir")); // 除錯工作目錄
        SwingUtilities.invokeLater(MainFrame::new);
    }
}