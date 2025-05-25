import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public abstract class Mouse {
    protected double x, y; // 當前位置
    protected int size; // 大小（像素）
    protected Point point1, point2, point3; // 三個移動點
    protected int value; // 分數
    protected int weight; // 重量
    protected boolean isCollected = false;
    protected Image image;
    protected int currentSegment = 0; // 路徑段：0(1→2), 1(2→3), 2(3→2), 3(2→1)
    protected double progress = 0;
    protected final double moveSpeed; // 老鼠自訂速度
    protected boolean isPausing = false; // 是否停頓
    protected int pauseTimer = 0; // 停頓計數器
    protected static final int PAUSE_DURATION = 50; // 1 秒（50 次更新，20ms/次）
    protected boolean isDestroyed = false;

    public Mouse(int size, Point point1, Point point2, Point point3, int value, int weight, String imageName, double moveSpeed) {
        this.size = size;
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.value = value;
        this.weight = weight;
        this.moveSpeed = moveSpeed;
        this.x = point1.x;
        this.y = point1.y; // 固定 Y
        this.image = ImageLoader.loadImage(imageName);
        if (image == null || image.getWidth(null) <= 0) {
            System.err.println("Failed to load " + imageName);
        }
    }

    public void update() {
        if (isCollected) return;

        if (isPausing) {
            // 處理停頓
            pauseTimer++;
            if (pauseTimer >= PAUSE_DURATION) {
                isPausing = false; // 停頓結束
                pauseTimer = 0;
                currentSegment = (currentSegment + 1) % 4; // 切換到下段
                progress = 0; // 重置進度
            }
            return; // 停頓期間不移動
        }

        // 正常移動
        progress += moveSpeed;
        if (progress >= 1) {
            progress = 1; // 停在終點
            isPausing = true; // 進入停頓
            pauseTimer = 0;
            // 不立即切換段，等待停頓結束
        }

        // 定義路徑：1→2, 2→3, 3→2, 2→1
        Point start, end;
        switch (currentSegment) {
            case 0: // 1→2
                start = point1;
                end = point2;
                break;
            case 1: // 2→3
                start = point2;
                end = point3;
                break;
            case 2: // 3→2
                start = point3;
                end = point2;
                break;
            case 3: // 2→1
                start = point2;
                end = point1;
                break;
            default:
                start = point1;
                end = point2;
        }

        // 水平移動，固定 Y
        x = start.x + (end.x - start.x) * progress;
        y = point1.y; // 強制 Y 固定
    }

    public void draw(Graphics g) {
        if (isCollected) return;
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.translate(x, y);
        // 判斷移動方向：end.x > start.x 則向右，翻轉圖片（包括停頓）
        Point start = getStartPoint();
        Point end = getEndPoint();
        if (end.x > start.x) {
            g2d.scale(-1, 1); // 向右移動或停頓，水平翻轉
        }
        if (image != null && image.getWidth(null) > 0) {
            g2d.drawImage(image, -size / 2, -size / 2, size, size, null);
        } else {
            g2d.setColor(Color.GRAY);
            g2d.fillOval(-size / 2, -size / 2, size, size);
            System.err.println("Drawing fallback oval for mouse");
        }
        g2d.setTransform(old);
    }

    public void drawAt(Graphics g, int x, int y) {
        // 抓取後不翻轉，保持左向
        if (image != null && image.getWidth(null) > 0) {
            g.drawImage(image, x - size / 2, y - size / 2, size, size, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillOval(x - size / 2, y - size / 2, size, size);
        }
    }

    private Point getStartPoint() {
        switch (currentSegment) {
            case 0: return point1;
            case 1: return point2;
            case 2: return point3;
            case 3: return point2;
            default: return point1;
        }
    }

    private Point getEndPoint() {
        switch (currentSegment) {
            case 0: return point2;
            case 1: return point3;
            case 2: return point2;
            case 3: return point1;
            default: return point2;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x - size / 2, (int)y - size / 2, size, size);
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        this.isCollected = collected;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

     public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.isDestroyed = destroyed;
    }
}