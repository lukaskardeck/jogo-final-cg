package entities;

import java.awt.Color;
import java.awt.Graphics;

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
        this.velX = 10;
    }

    public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void move() {
        this.posX += this.velX;
        this.posY += this.velY;
    }

    public void respawn(Player player) {
        this.posX = player.posX + player.width - this.width - 15;
        this.posY = player.posY + (player.height / 2) - (this.height / 2) + 16;
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect((int) this.posX, (int) this.posY, (int) this.width, (int) this.height);
    }
}
