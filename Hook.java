import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Hook {
    private int x, y;
    private double angle = Math.PI / 2; // 初始為 90 度（向下）
    private double time = 0; // 用於平滑擺動
    private double swingSpeed = 0.05; // 擺動速度
    private int length = 80;
    private int minLength = 80;
    private boolean isExtending = false;
    private boolean isReturning = false;
    private Mineral caughtMineral = null;
    private Image hookImage = ImageLoader.loadImage("hook.png");
    private ScoreManager scoreManager;
    private static final int WINDOW_WIDTH = 800; // 視窗寬度
    private static final int WINDOW_HEIGHT = 600; // 視窗高度

    public Hook(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        this.x = 400;
        this.y = 120;
        if (hookImage.getWidth(null) <= 0) {
            System.err.println("Failed to load hook.png");
        }
    }

    public void update() {
        if (!isExtending && !isReturning) {
            time += swingSpeed;
            angle = Math.PI / 2 + Math.sin(time) * Math.PI / 2; // 90 度 ± 90 度
        } else if (isExtending) {
            length += 10;
            // 檢查是否碰到視窗邊緣
            int endX = (int) (x + length * Math.cos(angle));
            int endY = (int) (y + length * Math.sin(angle));
            if (endX <= 0 || endX >= WINDOW_WIDTH || endY >= WINDOW_HEIGHT) {
                startReturn();
                System.out.println("Hook hit window edge: endX=" + endX + ", endY=" + endY);
            }
        } else if (isReturning) {
            int speed = (caughtMineral != null) ? Math.max(5, 20 - caughtMineral.getWeight()) : 20;
            length -= speed;
            if (length <= minLength) reset();
        }
    }

    public void launch() {
        if (!isExtending && !isReturning) {
            isExtending = true;
            System.out.println("Hook launched");
        } else {
            System.out.println("Cannot launch hook, isExtending: " + isExtending + ", isReturning: " + isReturning);
        }
    }

    public void startReturn() {
        isExtending = false;
        isReturning = true;
        System.out.println("Hook returning");
    }

    public boolean isReturning() {
        return isReturning;
    }

    public Mineral getCaughtMineral() {
        return caughtMineral;
    }

    public void checkCollision(List<Mineral> minerals, ScoreManager scoreManager) {
        if (caughtMineral != null) return;
        int hookX = (int) (x + length * Math.cos(angle));
        int hookY = (int) (y + length * Math.sin(angle));
        Rectangle hookRect = new Rectangle(hookX - 5, hookY - 5, 10, 10);
        for (Mineral m : minerals) {
            if (!m.isCollected() && hookRect.intersects(m.getBounds())) {
                caughtMineral = m;
                m.setCollected(true);
                startReturn();
                System.out.println("Caught mineral: " + m.getValue());
                break;
            }
        }
    }

    public boolean useBomb() {
        if (caughtMineral != null) {
            caughtMineral.setCollected(false);
            caughtMineral = null;
            startReturn();
            System.out.println("Bomb used successfully");
            return true;
        }
        System.out.println("No mineral to bomb");
        return false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int endX = (int) (x + length * Math.cos(angle));
        int endY = (int) (y + length * Math.sin(angle));
        
        // 繪製繩子（白色，粗細 3 像素）
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x, y, endX, endY);
        g2d.setStroke(new BasicStroke(1));
        
        // 保存畫布狀態
        AffineTransform oldTransform = g2d.getTransform();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.translate(endX, endY);
        g2d.rotate(angle); // 假設 hook.png 朝上
        int hookWidth = 20;
        int hookHeight = 20;
        g2d.drawImage(hookImage, -hookWidth / 2, -hookHeight / 2, hookWidth, hookHeight, null);
        g2d.setTransform(oldTransform);
        
        if (caughtMineral != null) caughtMineral.drawAt(g, endX, endY);
    }

    private void reset() {
        isReturning = false;
        if (caughtMineral != null) {
            scoreManager.addScore(caughtMineral.getValue());
            System.out.println("Score added: " + caughtMineral.getValue());
            caughtMineral = null;
        }
        length = minLength;
    }
}