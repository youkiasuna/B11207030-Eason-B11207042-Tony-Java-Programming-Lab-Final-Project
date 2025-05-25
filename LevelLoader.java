import java.awt.*;
import java.util.ArrayList;

public class LevelLoader {
    public static LevelConfig loadLevel(int level) {
        ArrayList<Mineral> minerals = new ArrayList<>();
        ArrayList<Mouse> mice = new ArrayList<>();
        int targetScore = 0;
        int timeLimit = 0;
        int bombCount = 0;

        if (level == 1) {
            minerals.add(new Mineral(
                MineralType.GOLD, 100, 300, 150, 4, 45, 45, 2
            ));
            minerals.add(new Mineral(
                MineralType.GOLD, 500, 300, 150, 4, 45, 45, 2
            ));
            minerals.add(new Mineral(
                MineralType.ROCK, 300, 400, 50, 7, 55, 55, 1
            ));
            mice.add(new NormalMouse(
                60, // size
                new Point(100, 300), // point1
                new Point(200, 300), // point2, Y 固定
                new Point(300, 300), // point3, Y 固定
                50, // value
                2,  // weight
                0.01 // moveSpeed (2 秒/段)
            ));
            mice.add(new DiamondMouse(
                50,
                new Point(300, 350), // point1
                new Point(400, 350), // point2, Y 固定
                new Point(500, 350), // point3, Y 固定
                200,
                5,
                0.02 // moveSpeed (1 秒/段)
            ));
            targetScore = 400;
            timeLimit = 60;
            bombCount = 3;
        }

        return new LevelConfig(minerals, mice, targetScore, timeLimit, bombCount);
    }
}