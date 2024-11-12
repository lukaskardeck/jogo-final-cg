import java.util.Random;

public class Enemy {
    public float posX;
    public float posY;
    public float velX;
    public float velY;
    public float width;
    public float height;

    public Random random;

    public Enemy() {
        this.random = new Random();
        this.width = 100;
        this.height = 50;
        this.posX = 900;
        this.posY = random.nextFloat(Principal.WINDOW_HEIGHT - this.height);
    }

    public void reposition() {
        this.posY = random.nextFloat(Principal.WINDOW_HEIGHT - this.height);
    }
}
