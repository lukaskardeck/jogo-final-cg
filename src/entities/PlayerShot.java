package entities;

import java.awt.Color;
import java.awt.Graphics;

import utils.Resource;

public class PlayerShot {
    public double posX;
    public double posY;
    public double velX;
    public double velY;
    public double width;
    public double height;
    public boolean isActive;

    public PlayerShot() {
        this.width = 6;
        this.height = 3;
        this.velX = 15;
    }

    public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void move() {
        double velocityFactor = Resource.getInstance().deltaTime * 60;
        this.posX += this.velX * velocityFactor;
        this.posY += this.velY * velocityFactor;
    }

    public void respawn(Player player) {
        this.posX = player.posX + player.width - this.width - 15;
        this.posY = player.posY + (player.height / 2) - (this.height / 2) + 16;
    }

    

    public void render(Graphics g) {
        g.setColor(Resource.getInstance().colorTransition());
        g.fillRect((int) this.posX, (int) this.posY, (int) this.width, (int) this.height);
    }
}
