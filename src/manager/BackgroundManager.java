package manager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Background;
import utils.Constants;

public class BackgroundManager {
    public BufferedImage img_scene_00;

    public Background scene01;
    public Background scene02;

    public BackgroundManager() {
        try {
            this.img_scene_00 = ImageIO.read(getClass().getResource("../assets/images/bg_01_1.jpg"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar a imagem");
        }

        scene01 = new Background(0, 0, img_scene_00);
        scene02 = new Background(scene01.posX + scene01.width, 0, img_scene_00);
    }

    public void moveScenes() {
        scene01.move();
        scene02.move();

        reposition();
    }

    public void reposition() {
        if (scene01.posX + scene01.width < 0)
            scene01.setPosition(scene02.posX + scene02.width, 0);
        else if (scene02.posX + scene02.width < 0)
            scene02.setPosition(scene01.posX + scene01.width, 0);
    }

    public void render(Graphics g) {
        // g.setColor(new Color(20, 150, 200));
        // g.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        g.drawImage(scene01.image, (int) scene01.posX, (int) scene01.posY, null);
        g.drawImage(scene02.image, (int) scene02.posX, (int) scene02.posY, null);
    }
}
