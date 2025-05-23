public class TimerManager {
    private long startTime;
    private int limitSeconds;

    public TimerManager(int seconds) {
        this.limitSeconds = seconds;
        startTime = System.currentTimeMillis();
    }

    public void update() {
        // 無需更新，時間透過 getTimeLeft 計算
    }

    public int getTimeLeft() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        return Math.max(0, limitSeconds - (int) elapsed);
    }

    public boolean isTimeUp() {
        return getTimeLeft() <= 0;
    }
}