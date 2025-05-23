import java.util.ArrayList;

public class LevelConfig {
    public ArrayList<Mineral> minerals;
    public ArrayList<Mouse> mice;
    public int targetScore;
    public int timeLimit;
    public int bombCount;

    public LevelConfig(ArrayList<Mineral> minerals, ArrayList<Mouse> mice, int targetScore, int timeLimit, int bombCount) {
        this.minerals = minerals;
        this.mice = mice;
        this.targetScore = targetScore;
        this.timeLimit = timeLimit;
        this.bombCount = bombCount;
    }
}