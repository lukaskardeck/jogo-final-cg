public class Player {
    public float width;
    public float height;
    public float posX;
    public float posY;
    public float velX;
    public float velY;

    public Player(float width, float height, float posX, float posY) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
    }

    public void move() {
        this.posX += this.velX;
        this.posY += this.velY;
    }
}
