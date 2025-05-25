import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel implements KeyListener {
    private GameManager manager;
    private int selectedLevel = 1;
    private static final int MAX_LEVEL = 9; // 假設最大關卡為 2

    public MenuPanel(GameManager manager) {
        this.manager = manager;
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.drawString("Select Level: " + selectedLevel, 250, 250);
        g.drawString("Use ←/→ to choose, Enter to start", 150, 300);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) selectedLevel = Math.max(1, selectedLevel - 1);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) selectedLevel = Math.min(MAX_LEVEL, selectedLevel + 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) manager.startGame(selectedLevel);
        repaint();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}