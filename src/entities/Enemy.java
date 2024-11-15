package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import utils.Constants;

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
        this.width = 70;
        this.height = 50;
        this.posX = Constants.WINDOW_WIDTH;
        this.posY = random.nextFloat(Constants.WINDOW_HEIGHT - this.height);
        this.velX = -5;
    }

    public void move() {
        this.posX += this.velX;
        this.posY += this.velY;
    }

    public void respawn() {
        this.posX = Constants.WINDOW_WIDTH;
        this.posY = new Random().nextFloat(Constants.WINDOW_HEIGHT - this.height);
    }


    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) this.posX, (int) this.posY, (int) this.width, (int) this.height);
    }
}
