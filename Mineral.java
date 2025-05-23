import java.awt.*;
import java.awt.geom.AffineTransform; // 添加此匯入

public class Mineral {
    private int x, y, width, height, value, weight;
    private Image image;
    private boolean collected = false;
    private double rotationAngle = 0; // 旋轉角度
    private double rotationSpeed = 0.05; // 旋轉速度

    public Mineral(int x, int y, int width, int height, int value, int weight, String imageName) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.value = value;
        this.weight = weight;
        this.image = ImageLoader.loadImage(imageName);
        if (image.getWidth(null) <= 0) {
            System.err.println("Failed to load image: " + imageName);
        }
    }

    public void updateRotation() {
        rotationAngle += rotationSpeed; // 每幀更新旋轉角度
    }

    public void draw(Graphics g) {
        if (!collected) {
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform oldTransform = g2d.getTransform();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.translate(x + width / 2, y + height / 2);
            g2d.rotate(rotationAngle);
            g2d.drawImage(image, -width / 2, -height / 2, width, height, null);
            g2d.setTransform(oldTransform);
        }
    }

    public void drawAt(Graphics g, int hookX, int hookY) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.translate(hookX, hookY + height / 2);
        g2d.rotate(rotationAngle);
        g2d.drawImage(image, -width / 2, -height / 2, width, height, null);
        g2d.setTransform(oldTransform);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean b) {
        collected = b;
    }
}