import java.util.*;

public class LevelLoader {
    public static final int MAX_LEVEL = 2; // 最大關卡數

    public static LevelConfig loadLevel(int level) {
        ArrayList<Mineral> list = new ArrayList<>();
        if (level == 1) {
            list.add(new Mineral(300, 400, 40, 40, 500, 5, "gold.png"));
            list.add(new Mineral(500, 420, 50, 50, 300, 10, "rock.png"));
            return new LevelConfig(list, 600, 60, 3);
        }
        if (level == 2) {
            list.add(new Mineral(200, 350, 60, 60, 800, 6, "gold.png"));
            list.add(new Mineral(400, 370, 50, 50, 400, 12, "rock.png"));
            list.add(new Mineral(600, 390, 40, 40, 300, 5, "gold.png"));
            return new LevelConfig(list, 1200, 60, 2);
        }
        // 預離關卡
        System.err.println("Invalid level: " + level + ", loading default level");
        return new LevelConfig(list, 500, 60, 3);
    }
}