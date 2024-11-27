package shapes;

import java.awt.Color;
import java.awt.Graphics;
import utils.Resource;

import utils.Constants;

public class RectangleShape implements Shape {
    public double posX;
    public double posY;
    public double velX;
    public double width;
    public double height;
    public Color color;
    // public boolean hasBorder;
    // public Color borderColor;
    // public int borderWidth;
    // public boolean bUp, bRight, bDown, bLeft;

    public RectangleShape(double posX, double posY, double width, double height, Color color) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public RectangleShape(double width, double height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void setPosition(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void positionNextTo(RectangleShape rect, boolean up) {
        // this.setPosition(rect.posX + rect.width, Constants.WINDOW_HEIGHT -
        // this.height);
        if (up)
            this.setPosition(rect.posX + rect.width, 0);
        else
            this.setPosition(rect.posX + rect.width, Constants.WINDOW_HEIGHT - this.height);
    }

    // public RectangleShape(float posX, float posY, float width, float height,
    // Color color, Color borderColor, int borderWidth, boolean bUp, boolean bRight,
    // boolean bDown,
    // boolean bLeft) {
    // this.posX = posX;
    // this.posY = posY;
    // this.width = width;
    // this.height = height;
    // this.color = color;
    // this.borderColor = borderColor;
    // this.borderWidth = borderWidth;
    // this.bUp = bUp;
    // this.bRight = bRight;
    // this.bDown = bDown;
    // this.bLeft = bLeft;
    // }

    // public void addBorder(Color bordeColor, int borderWidth, boolean bUp, boolean
    // bRight, boolean bDown,
    // boolean bLeft) {
    // this.hasBorder = true;
    // this.borderColor = bordeColor;
    // this.bUp = bUp;
    // this.bRight = bRight;
    // this.bDown = bDown;
    // this.bLeft = bLeft;
    // }

    // public void drawnWithBorder(Graphics g) {
    // g.setColor(borderColor);
    // g.fillRect((int) posX, (int) posY, (int) width, (int) height);

    // g.setColor(color);
    // g.fillRect((int) (posX + borderWidth * L), (int) (posY + borderWidth * U),
    // (int) (width - (borderWidth * R)),
    // (int) (height - (borderWidth * D)));
    // }

    @Override
    public void render(Graphics g) {

        g.setColor(color);
        g.fillRect((int) posX, (int) posY, (int) width, (int) height);

        // if (borderColor != null) {
        // g.setColor(borderColor);
        // if (bUp) {
        // g.fillRect((int) posX, (int) posY, (int) width, borderWidth);
        // }
        // if (bRight) {
        // g.fillRect((int) (posX + width - borderWidth), (int) posY, borderWidth, (int)
        // height);
        // }
        // if (bDown) {
        // g.fillRect((int) posX, (int) (posY + height - borderWidth), (int) width,
        // borderWidth);
        // }
        // if (bLeft) {
        // g.fillRect((int) posX, (int) posY, borderWidth, (int) height);
        // }
        // }

    }

    @Override
    public void move(float velX) {
        this.velX = velX * Resource.getInstance().deltaTime * 60;
        this.posX += this.velX;
    }
}
