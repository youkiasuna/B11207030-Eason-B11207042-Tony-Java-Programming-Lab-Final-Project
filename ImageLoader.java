import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageLoader {
    public static Image loadImage(String fileName) {
        try {
            // 使用資源路徑載入（從 src/images/）
            Image image = new ImageIcon(ImageLoader.class.getResource("/images/" + fileName)).getImage();
            if (image.getWidth(null) <= 0 || image.getHeight(null) <= 0) {
                throw new Exception("Invalid image");
            }
            System.out.println("Loaded image: /images/" + fileName);
            return image;
        } catch (Exception e) {
            System.err.println("Failed to load image: " + fileName + " - " + e.getMessage());
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); // 備用圖片
        }
    }
}