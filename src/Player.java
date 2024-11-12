import java.util.LinkedList;
import java.util.Queue;

public class Player {
    public final int NUM_SHOTS = 20;

    public float width;
    public float height;
    public float posX;
    public float posY;
    public float velX;
    public float velY;

    public Queue<PlayerShot> playerShot;

    public Player() {
        this.posX = 100;
        this.posY = 100;
        this.width = 100;
        this.height = 50;
        playerShot = new LinkedList<>();
        
        for (int i = 0; i < NUM_SHOTS; i++) {
            PlayerShot ps = new PlayerShot();
            playerShot.add(ps);
        }
    }
}
