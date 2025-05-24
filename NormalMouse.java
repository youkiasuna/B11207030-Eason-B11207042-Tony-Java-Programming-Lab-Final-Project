import java.awt.*;

public class NormalMouse extends Mouse {
    public NormalMouse(int size, Point point1, Point point2, Point point3, int value, int weight, double moveSpeed) {
        super(size, point1, new Point(point2.x, point1.y), new Point(point3.x, point1.y), value, weight, "mouse.png", moveSpeed);
    }
}