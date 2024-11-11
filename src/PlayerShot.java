public class PlayerShot {
    public float posX;
    public float posY;
    public float velX;
    public float velY;
    public float width;
    public float height;

    public PlayerShot() {
        this.width = 10;
        this.height = 5;
    }

    public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
