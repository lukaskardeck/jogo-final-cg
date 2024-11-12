public class PlayerShot {
    public float posX;
    public float posY;
    public float velX;
    public float velY;
    public float width;
    public float height;
    public boolean isActive;

    public PlayerShot() {
        this.width = 8;
        this.height = 4;
    }

    public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
