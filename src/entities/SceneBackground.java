package entities;

import java.awt.image.BufferedImage;

import utils.Constants;

public class SceneBackground {
    public float posX;
    public float posY;
    public float velX;
    public float width;
    public BufferedImage image;

    public SceneBackground(float posX, float posY, BufferedImage image) {
        this.posX = posX;
        this.posY = posY;
        this.velX = -Constants.SCENES_VELOCITY_MODULE;
        this.width = Constants.SCENE_WIDTH;
        this.image = image;
    }

    public void move() {
        this.posX += this.velX;
    }

    public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
