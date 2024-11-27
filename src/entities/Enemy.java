package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Random;

import javax.imageio.ImageIO;

import utils.Constants;
import utils.Resource;

public class Enemy {
    public double posX;
    public double posY;
    public double velX;
    public double velY;
    public double width;
    public double height;

    public Random random;

    public BufferedImage sprite;

    public Enemy() {
        this.random = new Random();
        this.width = 45;
        this.height = 36;
        this.posX = Constants.WINDOW_WIDTH;
        this.posY = random.nextDouble((608 - this.height) - (160 + this.height)) + (160 + this.height);
        this.velX = -Constants.ENEMY_VELOCITY_MODULE;
        this.velY = 8;

        try {
            this.sprite = ImageIO.read(getClass().getResource("../assets/images/inimigo.png"));
        } catch (Exception e) {
            System.out.println("Erro ao carregar a sprite do inimigo");
        }
    }

    public void move() {
        double velocityFactor = Resource.getInstance().deltaTime * 60;
        this.posX += this.velX * velocityFactor;
        this.posY += this.velY * velocityFactor;

        if (this.posY < 160) {
            this.posY = 160;
            this.velY *= -1;
        }

        else if (this.posY + this.height > 608) {
            this.posY = 608 - this.height;
            this.velY *= -1;
        }

    }

    public void respawn() {
        this.posX = Constants.WINDOW_WIDTH;
        // this.posY = new Random().nextFloat(Constants.WINDOW_HEIGHT - this.height);
        this.posY = random.nextDouble((608 - this.height) - (160 + this.height)) + (160 + this.height);
    }

    public void render(Graphics g) {
        g.setColor(new Color(250, 215, 150));
        g.fillRect((int) this.posX, (int) this.posY, (int) this.width, (int) this.height);
        // g.drawImage(sprite, (int) this.posX, (int) this.posY, null);
    }
}
