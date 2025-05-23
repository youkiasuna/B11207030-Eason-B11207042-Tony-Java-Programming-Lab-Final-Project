import java.awt.Image;

public enum MineralType {
    GOLD("gold.png"),
    ROCK("rock.png");

    private final String imageName;

    MineralType(String imageName) {
        this.imageName = imageName;
    }

    public Image getImage() {
        return ImageLoader.loadImage(imageName);
    }

    public String getImageName() {
        return imageName;
    }
}