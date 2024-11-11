public class Player {
    public float width;
    public float height;
    public float posX;
    public float posY;
    public float velX;
    public float velY;

    public PlayerShot playerShot;

    public Player(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.width = 100;
        this.height = 50;
        playerShot = new PlayerShot();
        playerShot.setPosition(this.posX + this.width - playerShot.width, this.posY + (this.height/2) - (playerShot.height/2));
    }

    /*public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }*/
}
