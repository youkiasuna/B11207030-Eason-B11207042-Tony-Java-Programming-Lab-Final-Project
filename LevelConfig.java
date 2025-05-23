import java.util.*;

public class LevelConfig {
    public ArrayList<Mineral> minerals;
    public int targetScore;
    public int timeLimit;
    public int bombCount;

    public LevelConfig(ArrayList<Mineral> minerals, int targetScore, int timeLimit, int bombCount) {
        this.minerals = minerals;
        this.targetScore = targetScore;
        this.timeLimit = timeLimit;
        this.bombCount = bombCount;
    }
}