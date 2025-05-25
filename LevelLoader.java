import java.awt.*;
import java.util.ArrayList;

public class LevelLoader {
    public static final int MAX_LEVEL = 9;

    public static LevelConfig loadLevel(int level) {
        ArrayList<Mineral> minerals = new ArrayList<>();
        ArrayList<Mouse> mice = new ArrayList<>();
        int targetScore = 0;
        int timeLimit = 0;
        int bombCount = 0;

        switch (level) {
            case 1:
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
                break;

            case 2: // 進階關 – 增加礦物數量與老鼠速度
                    // 礦物
                    minerals.add(new Mineral(MineralType.GOLD, 150, 280, 120, 4, 40, 40, 2));
                    minerals.add(new Mineral(MineralType.GOLD, 450, 320, 180, 4, 50, 50, 3));
                    minerals.add(new Mineral(MineralType.ROCK, 350, 420, 60, 6, 55, 55, 2));
                    minerals.add(new Mineral(MineralType.ROCK, 200, 420, 70, 6, 55, 55, 2));
                    minerals.add(new Mineral(MineralType.GOLD, 600, 350, 250, 5, 55, 55, 4));

                    // 老鼠 – 更快
                    mice.add(new NormalMouse(
                            60,
                            new Point(80, 300),
                            new Point(220, 300),
                            new Point(360, 300),
                            60,
                            2,
                            0.015 // 1.33 秒/段
                    ));
                    mice.add(new DiamondMouse(
                            50,
                            new Point(320, 340),
                            new Point(460, 340),
                            new Point(600, 340),
                            250,
                            5,
                            0.03 // 0.67 秒/段
                    ));

                    targetScore = 800;
                    timeLimit = 50;
                    bombCount = 3;
                    break;

                case 3: // 高手關 – 引入大形岩石與更高分黃金
                    minerals.add(new Mineral(MineralType.GOLD, 100, 300, 300, 6, 60, 60, 4));
                    minerals.add(new Mineral(MineralType.GOLD, 500, 300, 300, 6, 60, 60, 4));
                    minerals.add(new Mineral(MineralType.ROCK, 300, 430, 80, 10, 70, 70, 3));
                    minerals.add(new Mineral(MineralType.ROCK, 200, 430, 80, 10, 70, 70, 3));
                    minerals.add(new Mineral(MineralType.ROCK, 400, 430, 80, 10, 70, 70, 3));
                    minerals.add(new Mineral(MineralType.GOLD, 350, 350, 500, 8, 65, 65, 5)); // 大金塊

                    mice.add(new NormalMouse(
                            60,
                            new Point(70, 280),
                            new Point(250, 280),
                            new Point(430, 280),
                            80,
                            3,
                            0.02 // 1 秒/段
                    ));
                    mice.add(new DiamondMouse(
                            50,
                            new Point(300, 330),
                            new Point(450, 330),
                            new Point(600, 330),
                            300,
                            5,
                            0.035 // 0.57 秒/段
                    ));

                    targetScore = 1200;
                    timeLimit = 40;
                    bombCount = 4;
                    break;

                case 4: // 挑戰關 – 時間壓力、高分要求、障礙多
                    minerals.add(new Mineral(MineralType.GOLD, 120, 310, 200, 5, 55, 55, 3));
                    minerals.add(new Mineral(MineralType.GOLD, 520, 310, 200, 5, 55, 55, 3));
                    minerals.add(new Mineral(MineralType.GOLD, 320, 360, 400, 7, 70, 70, 5)); // 中央巨金
                    minerals.add(new Mineral(MineralType.ROCK, 230, 430, 90, 12, 75, 75, 4));
                    minerals.add(new Mineral(MineralType.ROCK, 410, 430, 90, 12, 75, 75, 4));

                    mice.add(new NormalMouse(
                            60,
                            new Point(60, 290),
                            new Point(280, 290),
                            new Point(500, 290),
                            90,
                            3,
                            0.025 // 0.8 秒/段
                    ));
                    mice.add(new DiamondMouse(
                            50,
                            new Point(300, 330),
                            new Point(450, 330),
                            new Point(600, 330),
                            350,
                            5,
                            0.04 // 0.5 秒/段
                    ));

                    targetScore = 1300;
                    timeLimit = 35; // 時間更緊湊
                    bombCount = 2;
                    break;

                case 5: // 炸彈教學 – 必須清除中央巨石才能挖到黃金
                    // 高硬度岩石（價值低）包住高價黃金
                    minerals.add(new Mineral(MineralType.ROCK, 350, 380, 30, 15, 80, 80, 5)); // 中央巨石
                    minerals.add(new Mineral(MineralType.ROCK, 270, 380, 30, 12, 70, 70, 4));
                    minerals.add(new Mineral(MineralType.ROCK, 430, 380, 30, 12, 70, 70, 4));
                    minerals.add(new Mineral(MineralType.GOLD, 350, 380, 800, 4, 60, 60, 2)); // 被包夾的黃金
                    minerals.add(new Mineral(MineralType.GOLD, 150, 320, 300, 5, 55, 55, 3));
                    minerals.add(new Mineral(MineralType.GOLD, 550, 320, 300, 5, 55, 55, 3));
                    mice.add(new NormalMouse(60, new Point(80, 300), new Point(300, 300), new Point(520, 300), 120, 4, 0.02));
                    targetScore = 1400; timeLimit = 30; bombCount = 4; break;

                case 6: // 隧道迷陣 – 一條窄縫，必須炸開側邊障礙
                    // 左右側大量岩石
                    for (int x = 50; x <= 650; x += 100) {
                        minerals.add(new Mineral(MineralType.ROCK, x, 410, 20, 14, 60, 60, 4));
                    }
                    minerals.add(new Mineral(MineralType.GOLD, 350, 350, 1000, 6, 65, 65, 3));
                    minerals.add(new Mineral(MineralType.GOLD, 350, 430, 700, 6, 65, 65, 3));
                    mice.add(new DiamondMouse(50, new Point(100, 310), new Point(350, 310), new Point(600, 310), 400, 6, 0.03));
                    targetScore = 2200; timeLimit = 35; bombCount = 5; break;

                case 7: // 快速老鼠與假黃金 – 誘導誤抓
                    minerals.add(new Mineral(MineralType.GOLD, 120, 330, 400, 5, 55, 55, 3));
                    minerals.add(new Mineral(MineralType.GOLD, 580, 330, 400, 5, 55, 55, 3));
                    // 一圈岩石混入低價礦
                    for (int x = 200; x <= 500; x += 100) {
                        minerals.add(new Mineral(MineralType.ROCK, x, 400, 10, 15, 70, 70, 4));
                    }
                    mice.add(new NormalMouse(60, new Point(70, 280), new Point(350, 280), new Point(630, 280), 150, 2, 0.03)); // 更快
                    mice.add(new DiamondMouse(50, new Point(350, 320), new Point(480, 320), new Point(610, 320), 500, 5, 0.05));
                    targetScore = 1400; timeLimit = 30; bombCount = 4; break;

                case 8: // 雙層防禦 – 黃金被兩層岩石包圍
                    // 外圈
                    for (int angle = 0; angle < 360; angle += 45) {
                        double rad = Math.toRadians(angle);
                        int x = (int) (350 + 120 * Math.cos(rad));
                        int y = (int) (380 + 70 * Math.sin(rad));
                        minerals.add(new Mineral(MineralType.ROCK, x, y, 10, 16, 60, 60, 5));
                    }
                    // 內圈
                    for (int angle = 0; angle < 360; angle += 60) {
                        double rad = Math.toRadians(angle);
                        int x = (int) (350 + 70 * Math.cos(rad));
                        int y = (int) (380 + 40 * Math.sin(rad));
                        minerals.add(new Mineral(MineralType.ROCK, x, y, 10, 14, 55, 55, 4));
                    }
                    minerals.add(new Mineral(MineralType.GOLD, 350, 380, 1500, 4, 60, 60, 2));
                    mice.add(new DiamondMouse(50, new Point(100, 300), new Point(350, 300), new Point(600, 300), 600, 5, 0.045));
                    targetScore = 2100; timeLimit = 40; bombCount = 6; break;

                case 9: // 終極地獄 – 超重岩石 + 限時 + 高速鑽石鼠
                    // 地圖底部鋪滿超高重量岩石，必炸
                    for (int x = 50; x <= 650; x += 100) {
                        minerals.add(new Mineral(MineralType.ROCK, x, 300, 10, 30, 80, 80, 6));
                    }
                    // 中央巨金
                    minerals.add(new Mineral(MineralType.GOLD, 350, 350, 3000, 8, 80, 80, 3));
                    // 干擾用低價金塊
                    minerals.add(new Mineral(MineralType.GOLD, 200, 300, 200, 6, 55, 55, 3));
                    minerals.add(new Mineral(MineralType.GOLD, 500, 300, 200, 6, 55, 55, 3));
                    mice.add(new DiamondMouse(50, new Point(70, 480), new Point(350, 280), new Point(630, 280), 800, 6, 0.08)); // 極速
                    targetScore = 4200; timeLimit = 35; bombCount = 0; break;

                default:
                    // 若關卡不存在，回傳第一關設定
                    return loadLevel(1);
            }

        return new LevelConfig(minerals, mice, targetScore, timeLimit, bombCount);
    }
}