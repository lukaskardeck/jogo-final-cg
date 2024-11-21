package entities;

import java.awt.Graphics;
import java.util.ArrayList;

import shapes.RectangleShape;
import shapes.Shape;

public class Terrain {
    public float posX;
    public float posY;
    public float width;
    public float height;
    public ArrayList<Shape> shapes;

    public Terrain(float posX, float posY, float width, float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.shapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void addShapeNextTo(RectangleShape rect, RectangleShape nextShape) {
        
    }

    public void move(float deltaX) {
        posX += deltaX; // Move a posição do terreno
    
        // Move todos os shapes do terreno
        for (Shape shape : shapes) {
            shape.move(deltaX);
        }
    }

    public void reposition(float newPosX) {
        float deltaX = newPosX - posX;
        posX = newPosX; // Atualiza a posição do terreno
    
        // Reposiciona os shapes
        for (Shape shape : shapes) {
            shape.move(deltaX);
        }
    }
    

    public void render(Graphics g) {
        for (Shape shape : shapes) {
            shape.render(g);
        }
    }
}