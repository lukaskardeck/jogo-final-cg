package manager;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import shapes.RectangleShape;
import shapes.Shape;
import utils.Constants;

public class TerrainManager {
    public ArrayList<Shape> shapes;
    public ArrayList<Shape> upShapes;
    public ArrayList<Shape> downShapes;
    public ArrayList<ArrayList<Shape>> shapesTest;
    // public RectangleShape 
    //     r1,
    //     r2, 
    //     r3, 
    //     r4,
    //     r5,
    //     r6,
    //     r7,
    //     r8,
    //     r9,
    //     r10,
    //     r11,
    //     r12,
    //     r13,
    //     r14,
    //     r15,
    //     r16;

    public TerrainManager() {
        shapes = new ArrayList<>();
        upShapes = new ArrayList<>();
        downShapes = new ArrayList<>();
        shapesTest = new ArrayList<>();

        Color terrainColor = new Color(115, 45, 40);

        int height = Constants.MAX_HEIGHT_TERRAIN;

        // Terrenos da parte superior
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                RectangleShape r1 = new RectangleShape(512, height, terrainColor);
                if (i != 0) r1.positionNextTo((RectangleShape) upShapes.get(i - 1), true);
                else r1.setPosition(-256, 50);
                upShapes.add(r1);
            }
            else {
                RectangleShape r1 = new RectangleShape(512, height/2, terrainColor);
                r1.positionNextTo((RectangleShape) upShapes.get(i - 1), true);
                upShapes.add(r1);
            }
        }

        for (int i = 0; i < upShapes.size(); i++) {
            shapes.add(upShapes.get(i));
        }

        // shapesTest.add(upShapes);

        // Terrenos da parte inferior
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                RectangleShape r1 = new RectangleShape(512, height, terrainColor);
                if (i == 0) r1.setPosition(0, Constants.WINDOW_HEIGHT - r1.height);
                else r1.positionNextTo((RectangleShape) downShapes.get(i-1), false);
                downShapes.add(r1);
            }
            else {
                RectangleShape r1 = new RectangleShape(512, height/2, terrainColor);
                r1.positionNextTo((RectangleShape) downShapes.get(i-1), false);
                downShapes.add(r1);
            }
        }

        for (int i = 0; i < downShapes.size(); i++) {
            shapes.add(downShapes.get(i));
        }

        // shapesTest.add(downShapes);

        // r1 = new RectangleShape(317, 136, terrainColor);
        // r1.setPosition(0, Constants.WINDOW_HEIGHT - r1.height);
        // shapes.add(r1);

        // r2 = new RectangleShape(353, 270, terrainColor);
        // r2.positionNextTo(r1);
        // shapes.add(r2);

        // r3 = new RectangleShape(354, 462, terrainColor);
        // r3.positionNextTo(r2);
        // shapes.add(r3);

        // r4 = new RectangleShape(164, 270, terrainColor);
        // r4.positionNextTo(r3);
        // shapes.add(r4);

        // r5 = new RectangleShape(696, 136, terrainColor);
        // r5.positionNextTo(r4);
        // shapes.add(r5);

        // r6 = new RectangleShape(164, 270, terrainColor);
        // r6.positionNextTo(r5);
        // shapes.add(r6);

        // r7 = new RectangleShape(201, 534, terrainColor);
        // r7.positionNextTo(r6);
        // shapes.add(r7);

        // r8 = new RectangleShape(201, 400, terrainColor);
        // r8.positionNextTo(r7);
        // shapes.add(r8);

        // r9 = new RectangleShape(201, 266, terrainColor);
        // r9.positionNextTo(r8);
        // shapes.add(r9);

        // r10 = new RectangleShape(421, 136, terrainColor);
        // r10.positionNextTo(r9);
        // shapes.add(r10);

        // r11 = new RectangleShape(168, 205, terrainColor);
        // r11.positionNextTo(r10);
        // shapes.add(r11);

        // r12 = new RectangleShape(168, 136, terrainColor);
        // r12.positionNextTo(r11);
        // shapes.add(r12);

        // r13 = new RectangleShape(168, 205, terrainColor);
        // r13.positionNextTo(r12);
        // shapes.add(r13);

        // r14 = new RectangleShape(168, 136, terrainColor);
        // r14.positionNextTo(r13);
        // shapes.add(r14);

        // r15 = new RectangleShape(168, 205, terrainColor);
        // r15.positionNextTo(r14);
        // shapes.add(r15);

        // r16 = new RectangleShape(184, 136, terrainColor);
        // r16.positionNextTo(r15);
        // shapes.add(r16);
    }

    public void moveTerrain() {
        for (Shape shape : shapes) {
            shape.move(-3);
        }

        // for (Shape shape : upShapes) {
        //     shape.move(-3);
        // }

        // for (Shape shape : downShapes) {
        //     shape.move(-3);
        // }
    
        // Reposiciona shapes que saíram da tela
        for (int i = 0; i < upShapes.size(); i++) {
            Shape shape = upShapes.get(i);
    
            if (shape instanceof RectangleShape) {
                RectangleShape rect = (RectangleShape) shape;
    
                if (rect.posX + rect.width <= 0) {
                    if (i == 0) {
                        // Reposiciona o primeiro shape ao fundo do último
                        RectangleShape lastShape = (RectangleShape) upShapes.get(upShapes.size() - 1);
                        rect.posX = lastShape.posX + lastShape.width;
                    } else {
                        // Reposiciona os outros shapes ao fundo do shape anterior
                        RectangleShape prevShape = (RectangleShape) upShapes.get(i - 1);
                        rect.posX = prevShape.posX + prevShape.width;
                    }
                }
            }
        }

        for (int i = 0; i < downShapes.size(); i++) {
            Shape shape = downShapes.get(i);
    
            if (shape instanceof RectangleShape) {
                RectangleShape rect = (RectangleShape) shape;
    
                if (rect.posX + rect.width <= 0) {
                    if (i == 0) {
                        // Reposiciona o primeiro shape ao fundo do último
                        RectangleShape lastShape = (RectangleShape) downShapes.get(downShapes.size() - 1);
                        rect.posX = lastShape.posX + lastShape.width;
                    } else {
                        // Reposiciona os outros shapes ao fundo do shape anterior
                        RectangleShape prevShape = (RectangleShape) downShapes.get(i - 1);
                        rect.posX = prevShape.posX + prevShape.width;
                    }
                }
            }
        }
    }

    // public void colorTransition() {
    //     for (Shape shape : shapes) {
    //         if (shape instanceof RectangleShape rect) {
    //             rect.color = Resource.getInstance().colorTransition();
    //         }
    //     }
    // }

    public void renderTerrains(Graphics g) {
        for (Shape shape : shapes) {
            shape.render(g);
        }

        // for (Shape shape : upShapes) {
        //     shape.render(g);
        // }

        // for (Shape shape : downShapes) {
        //     shape.render(g);
        // }
    }
}
