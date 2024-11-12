public class Player {
    public float width;
    public float height;
    public float posX;
    public float posY;
    public float velX;
    public float velY;

    public PlayerShot playerShot;

    public Player() {
        this.posX = 100;
        this.posY = 100;
        this.width = 100;
        this.height = 50;
        playerShot = new PlayerShot();
        playerShot.setPosition(this.posX + this.width - playerShot.width, this.posY + (this.height/2) - (playerShot.height/2));
    }
}
