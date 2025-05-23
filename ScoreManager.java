public class ScoreManager {
    private int score = 0;
    private int targetScore;

    public ScoreManager(int targetScore) {
        this.targetScore = targetScore;
    }

    public void addScore(int value) {
        score += value;
    }

    public int getScore() {
        return score;
    }

    public boolean hasMetGoal() {
        return score >= targetScore;
    }
}