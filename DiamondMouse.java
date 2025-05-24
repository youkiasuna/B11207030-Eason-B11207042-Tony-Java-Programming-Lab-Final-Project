import java.awt.*;

public class DiamondMouse extends Mouse {
    public DiamondMouse(int size, Point point1, Point point2, Point point3, int value, int weight, double moveSpeed) {
        super(size, point1, new Point(point2.x, point1.y), new Point(point3.x, point1.y), value, weight, "diamond_mouse.png", moveSpeed);
    }
}