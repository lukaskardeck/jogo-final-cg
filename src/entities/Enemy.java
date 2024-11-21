package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Random;

import javax.imageio.ImageIO;

import utils.Constants;

public class Enemy {
    public float posX;
    public float posY;
    public float velX;
    public float velY;
    public float width;
    public float height;

    public Random random;

    public BufferedImage sprite;

    public Enemy() {
        this.random = new Random();
        this.width = 45;
        this.height = 36;
        this.posX = Constants.WINDOW_WIDTH;
        this.posY = random.nextFloat((568 - this.height) - (200 + this.height)) + (200 + this.height);
        this.velX = -Constants.ENEMY_VELOCITY_MODULE;
        this.velY = 10;

        try {
            this.sprite = ImageIO.read(getClass().getResource("../assets/images/inimigo.png"));
        } catch (Exception e) {
            System.out.println("Erro ao carregar a sprite do inimigo");
        }
    }

    public void move() {
        this.posX += this.velX;

        if (this.posY < 200 || this.posY + this.height > 568) {
            this.velY *= -1;
        }

        this.posY += this.velY;
    }

    public void respawn() {
        this.posX = Constants.WINDOW_WIDTH;
        // this.posY = new Random().nextFloat(Constants.WINDOW_HEIGHT - this.height);
        this.posY = random.nextFloat((568 - this.height) - (200 + this.height)) + (200 + this.height);
    }

    public void render(Graphics g) {
        g.setColor(new Color(250, 215, 150));
        g.fillRect((int) this.posX, (int) this.posY, (int) this.width, (int) this.height);
        // g.drawImage(sprite, (int) this.posX - 5, (int) this.posY - 3, null);
    }
}
