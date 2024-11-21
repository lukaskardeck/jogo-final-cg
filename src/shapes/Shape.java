package shapes;

import java.awt.Graphics;

public interface Shape {
    void render(Graphics g); // Método para desenhar a forma
    void move(float velX); 
}
