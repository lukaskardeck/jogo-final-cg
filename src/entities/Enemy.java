package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Random;

import javax.imageio.ImageIO;

import utils.Constants;
import utils.Resource;

public class Enemy {
    public static final float ENEMY_VELX_MODULE = 2;
    public static final float ENEMY_VELY_MODULE = 8;

    public double posX;
    public double posY;
    public double velX;
    public double velY;
    public double width;
    public double height;

    public Random random;

    public BufferedImage sprite;
    public BufferedImage alienGreen;
    public BufferedImage alienBlue;
    public BufferedImage alienYellow;
    public BufferedImage[] spriteAliens;

    public Enemy() {
        this.random = new Random();
        this.width = 45;
        this.height = 45;
        this.posX = Constants.WINDOW_WIDTH;
        this.posY = random.nextDouble((Constants.WINDOW_HEIGHT - Constants.MAX_HEIGHT_TERRAIN - 20 - this.height) - (Constants.MAX_HEIGHT_TERRAIN + 20 + this.height)) + (Constants.MAX_HEIGHT_TERRAIN + 20 + this.height);
        this.velX = -ENEMY_VELX_MODULE;
        this.velY = this.random.nextBoolean() ? ENEMY_VELY_MODULE : -ENEMY_VELY_MODULE;
        //System.out.println(this.velY);

        try {
            // this.sprite = ImageIO.read(getClass().getResource("../assets/images/Alien.png"));
            this.alienGreen = ImageIO.read(getClass().getResource("../assets/images/alien_green.png"));
            this.alienBlue = ImageIO.read(getClass().getResource("../assets/images/alien_blue.png"));
            this.alienYellow = ImageIO.read(getClass().getResource("../assets/images/alien_yellow.png"));

        } catch (Exception e) {
            System.out.println("Erro ao carregar a sprite do inimigo");
        }

        spriteAliens = new BufferedImage[]{alienGreen, alienBlue, alienYellow};
        this.sprite = spriteAliens[random.nextInt(spriteAliens.length)];
    }

    public void move() {
        double velocityFactor = Resource.getInstance().deltaTime * 60;
        this.posX += this.velX * velocityFactor;
        this.posY += this.velY * velocityFactor;

        if (this.posY < Constants.MAX_HEIGHT_TERRAIN + 20 + 50) {
            this.posY = Constants.MAX_HEIGHT_TERRAIN + 20 + 50;
            this.velY *= -1;
        }

        else if (this.posY + this.height > Constants.WINDOW_HEIGHT - Constants.MAX_HEIGHT_TERRAIN - 20) {
            this.posY = Constants.WINDOW_HEIGHT - Constants.MAX_HEIGHT_TERRAIN - 20 - this.height;
            this.velY *= -1;
        }

    }

    public void respawn() {
        this.posX = Constants.WINDOW_WIDTH;
        // this.posY = new Random().nextFloat(Constants.WINDOW_HEIGHT - this.height);
        // this.posY = random.nextDouble((608 - this.height) - (160 + this.height)) + (160 + this.height);
        this.posY = random.nextDouble((Constants.WINDOW_HEIGHT - Constants.MAX_HEIGHT_TERRAIN - 20 - this.height) - (Constants.MAX_HEIGHT_TERRAIN + 20 + this.height)) + (Constants.MAX_HEIGHT_TERRAIN + 20 + this.height);
    }

    public void render(Graphics g) {
        // g.setColor(new Color(250, 215, 150));
        // g.fillRect((int) this.posX, (int) this.posY, (int) this.width, (int) this.height);
        g.drawImage(sprite, (int) this.posX, (int) this.posY, null);
    }
}
