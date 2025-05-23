import java.awt.Image;

public enum MineralType {
    GOLD("gold.png", 100, 3, 40, 40),
    ROCK("rock.png", 30, 6, 50, 50);

    private final String imageName;
    private final int value;
    private final int weight;
    private final int width;
    private final int height;

    MineralType(String imageName, int value, int weight, int width, int height) {
        this.imageName = imageName;
        this.value = value;
        this.weight = weight;
        this.width = width;
        this.height = height;
    }

    public Image getImage() {
        return ImageLoader.get(imageName);
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
