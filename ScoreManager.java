public class ScoreManager {
    private int score;
    private int targetScore;

    public ScoreManager(int targetScore) {
        this.score = 0;
        this.targetScore = targetScore;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int value) {
        score += value;
    }

    public boolean hasMetGoal() {
        return score >= targetScore;
    }

    // 新增 getTargetScore
    public int getTargetScore() {
        return targetScore;
    }
}