import java.awt.*;
import java.awt.geom.AffineTransform; // 添加此匯入

public class Mineral {
    private double x, y;
    private MineralType type;
    private int value;
    private int weight;
    private int width;
    private int height;
    private boolean isCollected = false;
    private double rotation = 0;
    private int rotationSpeed;
    private boolean isDestroyed = false;
    private double rotationAngle = 0;

    public Mineral(MineralType type, int x, int y, int value, int weight, int width, int height, int rotationSpeed) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.value = value;
        this.weight = weight;
        this.width = width;
        this.height = height;
        this.rotationSpeed = rotationSpeed;
    }

    public void update() {
        if (isCollected || isDestroyed) return;
        rotationAngle = (rotationAngle + rotationSpeed) % 360;
    }

    public void updateRotation() {
        if (!isCollected) {
            rotation += rotationSpeed;
            if (rotation >= 360) rotation -= 360;
        }
    }

    public void draw(Graphics g) {
        if (isCollected) return;
        Image image = type.getImage();
        if (image != null && image.getWidth(null) > 0) {
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform oldTransform = g2d.getTransform();
            g2d.translate(x, y);
            g2d.rotate(Math.toRadians(rotation));
            g2d.drawImage(image, -width / 2, -height / 2, width, height, null);
            g2d.setTransform(oldTransform);
        } else {
            g.setColor(Color.YELLOW);
            g.fillOval((int)x - width / 2, (int)y - height / 2, width, height);
        }
    }

    public void drawAt(Graphics g, int x, int y) {
        Image image = type.getImage();
        if (image != null && image.getWidth(null) > 0) {
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform oldTransform = g2d.getTransform();
            g2d.translate(x, y);
            g2d.rotate(Math.toRadians(rotation));
            g2d.drawImage(image, -width / 2, -height / 2, width, height, null);
            g2d.setTransform(oldTransform);
        } else {
            g.setColor(Color.YELLOW);
            g.fillOval(x - width / 2, y - height / 2, width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x - width / 2, (int)y - height / 2, width, height);
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