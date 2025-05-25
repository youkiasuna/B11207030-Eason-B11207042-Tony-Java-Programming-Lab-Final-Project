import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Hook {
    private int x, y;
    private double angle = Math.PI / 2;
    private double time = 0;
    private double swingSpeed = 0.05;
    private int length = 80;
    private int minLength = 80;
    private boolean isExtending = false;
    private boolean isReturning = false;
    private Object caughtItem = null; // 支援 Mineral 或 Mouse
    private Image hookImage = ImageLoader.loadImage("hook.png");
    private ScoreManager scoreManager;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private SoundPlayer sfxPlayer;

    public Hook(ScoreManager scoreManager, SoundPlayer sfxPlayer) {
        this.scoreManager = scoreManager;
        this.x = 400;
        this.y = 120;
        this.sfxPlayer = sfxPlayer;
        if (hookImage == null || hookImage.getWidth(null) <= 0) {
            System.err.println("Failed to load hook.png");
        }
    }

    public void update(List<Mineral> minerals, List<Mouse> mice) {
        if (!isExtending && !isReturning) {
            time += swingSpeed;
            angle = Math.PI / 2 + Math.sin(time) * Math.PI / 2;
        } else if (isExtending) {
            length += 10;
            checkCollision(minerals, mice);
            if (caughtItem == null) {
                int endX = (int) (x + length * Math.cos(angle));
                int endY = (int) (y + length * Math.sin(angle));
                if (endX <= 0 || endX >= WINDOW_WIDTH || endY >= WINDOW_HEIGHT) {
                    startReturn();
                    System.out.println("Hook hit window edge: endX=" + endX + ", endY=" + endY);
                }
            }
        } else if (isReturning) {
            int speed = (caughtItem != null) ? Math.max(5, 20 - getItemWeight()) : 20;
            length -= speed;
            if (length <= minLength) {
                reset();
                System.out.println("Hook reset after return");
            }
        }
    }

    public void launch() {
        if (!isExtending && !isReturning) {
            isExtending = true;
            System.out.println("Hook launched");
        }
    }

    public void startReturn() {
        isExtending = false;
        isReturning = true;
        System.out.println("Hook returning");
    }

    public boolean isReturning() {
        return isReturning;
    }

    public Object getCaughtItem() {
        return caughtItem;
    }

    public void checkCollision(List<Mineral> minerals, List<Mouse> mice) {
        if (caughtItem != null) return;
        int hookX = (int) (x + length * Math.cos(angle));
        int hookY = (int) (y + length * Math.sin(angle));
        Rectangle hookRect = new Rectangle(hookX - 5, hookY - 5, 10, 10);
        for (Mineral m : minerals) {
            if (!m.isCollected() && !m.isDestroyed() && hookRect.intersects(m.getBounds())) {
                caughtItem = m;
                m.setCollected(true);
                startReturn();
                System.out.println("Caught mineral: " + m.getValue());
                break;
            }
        }
        if (caughtItem == null) {
            for (Mouse m : mice) {
                if (!m.isCollected() && !m.isDestroyed() && hookRect.intersects(m.getBounds())) {
                    caughtItem = m;
                    m.setCollected(true);
                    startReturn();
                    System.out.println("Caught mouse: " + m.getValue());
                    break;
                }
            }
        }
    }

    public boolean useBomb() {
        if (caughtItem != null) {
            if (caughtItem instanceof Mineral) {
                ((Mineral)caughtItem).setDestroyed(true);
                System.out.println("Bombed mineral: " + ((Mineral)caughtItem).getValue());
            } else if (caughtItem instanceof Mouse) {
                ((Mouse)caughtItem).setDestroyed(true);
                System.out.println("Bombed mouse: " + ((Mouse)caughtItem).getValue());
            }
            caughtItem = null;
            isExtending = false; // 確保停止延伸
            isReturning = true; // 明確開始返回
            startReturn();
            System.out.println("Bomb used successfully");
            return true;
        }
        System.out.println("No item to bomb");
        return false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int endX = (int) (x + length * Math.cos(angle));
        int endY = (int) (y + length * Math.sin(angle));
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x, y, endX, endY);
        g2d.setStroke(new BasicStroke(1));
        if (hookImage != null && hookImage.getWidth(null) > 0) {
            AffineTransform oldTransform = g2d.getTransform();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.translate(endX, endY);
            g2d.rotate(angle);
            int hookWidth = 20;
            int hookHeight = 20;
            g2d.drawImage(hookImage, -hookWidth / 2, -hookHeight / 2, hookWidth, hookHeight, null);
            g2d.setTransform(oldTransform);
        } else {
            g2d.setColor(Color.RED);
            g2d.fillRect(endX - 10, endY - 10, 20, 20);
        }
        if (caughtItem != null) {
            if (caughtItem instanceof Mineral) {
                ((Mineral)caughtItem).drawAt(g, endX, endY);
            } else if (caughtItem instanceof Mouse) {
                ((Mouse)caughtItem).drawAt(g, endX, endY);
            }
        }
    }

    private void reset() {
        isReturning = false;
        if (caughtItem != null) {
            sfxPlayer.playSound("sounds/score.wav", false);
            scoreManager.addScore(getItemValue());
            System.out.println("Score added: " + getItemValue());
            caughtItem = null;
        }
        length = minLength;
    }

    private int getItemValue() {
        if (caughtItem instanceof Mineral) {
            
            return ((Mineral)caughtItem).getValue();
        } else if (caughtItem instanceof Mouse) {
            return ((Mouse)caughtItem).getValue();
        }
        return 0;
    }

    private int getItemWeight() {
        if (caughtItem instanceof Mineral) {
            return ((Mineral)caughtItem).getWeight();
        } else if (caughtItem instanceof Mouse) {
            return ((Mouse)caughtItem).getWeight();
        }
        return 0;
    }
}