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
                MineralType.GOLD, // type
                100, // x
                300, // y
                150, // value
                4,   // weight
                45,  // width
                45,  // height
                2    // rotationSpeed
            ));
            minerals.add(new Mineral(
                MineralType.ROCK,
                300,
                400,
                50,  // value
                900,   // weight
                55,  // width
                55,  // height
                1
            ));
            mice.add(new NormalMouse(
                60,
                new Point(100, 300), new Point(200, 300), new Point(200, 300),
                50,
                2
            ));
            mice.add(new DiamondMouse(
                60,
                new Point(300, 350), new Point(400, 350), new Point(500, 350),
                200,
                5
            ));
            targetScore = 400; // 調整以反映新分數
            timeLimit = 60;
            bombCount = 3;
        }

        return new LevelConfig(minerals, mice, targetScore, timeLimit, bombCount);
    }
}