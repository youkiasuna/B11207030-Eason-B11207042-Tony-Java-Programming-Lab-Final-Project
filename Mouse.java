import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Mouse {
    protected double x, y; // 當前位置
    protected int size; // 大小（像素）
    protected Point point1, point2, point3; // 三個移動點
    protected int value; // 分數
    protected int weight; // 重量
    protected boolean isCollected = false;
    protected Image image;
    protected int currentPoint = 0; // 當前目標點（0, 1, 2）
    protected double progress = 0; // 移動進度（0 到 1）
    protected static final double MOVE_SPEED = 0.02; // 每更新移動 2%（約 2 秒/段）

    public Mouse(int size, Point point1, Point point2, Point point3, int value, int weight, String imageName) {
        this.size = size;
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.value = value;
        this.weight = weight;
        this.x = point1.x;
        this.y = point1.y;
        this.image = ImageLoader.loadImage(imageName);
        if (image == null || image.getWidth(null) <= 0) {
            System.err.println("Failed to load " + imageName);
        }
    }

    public void update() {
        if (isCollected) return;
        progress += MOVE_SPEED;
        if (progress >= 1) {
            progress = 0;
            currentPoint = (currentPoint + 1) % 3; // 循環：0 -> 1 -> 2 -> 0
        }
        Point start = getPoint(currentPoint);
        Point end = getPoint((currentPoint + 1) % 3);
        x = start.x + (end.x - start.x) * progress;
        y = start.y + (end.y - start.y) * progress;
    }

    private Point getPoint(int index) {
        switch (index) {
            case 0: return point1;
            case 1: return point2;
            case 2: return point3;
            default: return point1;
        }
    }

    public void draw(Graphics g) {
        if (isCollected) return;
        if (image != null && image.getWidth(null) > 0) {
            g.drawImage(image, (int)x - size / 2, (int)y - size / 2, size, size, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillOval((int)x - size / 2, (int)y - size / 2, size, size);
            System.err.println("Drawing fallback oval for mouse");
        }
    }

    public void drawAt(Graphics g, int x, int y) {
        if (image != null && image.getWidth(null) > 0) {
            g.drawImage(image, x - size / 2, y - size / 2, size, size, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillOval(x - size / 2, y - size / 2, size, size);
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
}