package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class TriangleShape implements Shape {
    public int[] xPoints; 
    public int[] yPoints;
    public Color color;

    public TriangleShape(int[] xPoints, int[] yPoints, Color color) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.color = color;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }

    @Override
    public void move(float velX) {
        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] += velX;
        }
    }
    

}