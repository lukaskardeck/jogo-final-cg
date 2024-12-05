package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;

import manager.InputManager;
import utils.Constants;
import utils.Resource;

public class Player {
    public final int NUM_SHOTS = 20;

    public int life;
    public int points;

    public double width;
    public double height;
    public double posX;
    public double posY;
    public double velX;
    public double velY;

    double timeShot = 0;

    public Stack<PlayerShot> stackShots;
    public ArrayList<PlayerShot> listShots;

    public BufferedImage sprite;

    public Player() {
        this.life = 3;
        this.life = 0;

        this.width = 70;
        this.height = 80;
        this.posX = 25;
        this.posY = Constants.WINDOW_HEIGHT / 2 - this.height;

        this.stackShots = new Stack<>();
        for (int i = 0; i < Constants.NUM_SHOTS; i++) {
            stackShots.push(new PlayerShot());
        }
        this.listShots = new ArrayList<>();

        try {
            this.sprite = ImageIO.read(getClass().getResource("../assets/images/player.png"));
        } catch (Exception e) {
            System.out.println("Erro ao carregar sprite do player");
        }
    }

    public void move() {
        double velocityFactor = Resource.getInstance().deltaTime * 60;
        this.posX += this.velX * velocityFactor;
        this.posY += this.velY * velocityFactor;
    }

    public void updateVelocityPlayer(InputManager input) {
        float pvm = Constants.PLAYER_VELOCITY_MODULE;
        this.velX = this.velY = 0;

        /* MOVIMENTAÇÃO DO PLAYER COM O TECLADO */
        if (input.key_player_up) {
            this.velY = -pvm;
            if (input.key_player_right)
                this.velX = pvm;
            else if (input.key_player_left)
                this.velX = -pvm;
        } else if (input.key_player_down) {
            this.velY = pvm;
            if (input.key_player_right)
                this.velX = pvm;
            else if (input.key_player_left)
                this.velX = -pvm;
        } else if (input.key_player_right)
            this.velX = pvm;
        else if (input.key_player_left)
            this.velX = -pvm;
    }

    public void spawnShot(InputManager inputHandler) {
        timeShot += Resource.getInstance().deltaTime;
        double intervalShot = 0.7;
        int spaceBetweenShots = 25;

        if (inputHandler.shot) {
            if ((timeShot >= intervalShot)) {
                for (int i = 0; i < 3; i++) { // Gera 3 disparos seguidos
                    if (!stackShots.isEmpty()) {
                        PlayerShot ps = stackShots.pop();
                        ps.respawn(this); // Configura posição inicial baseada no jogador
                        ps.posX += i * spaceBetweenShots;
                        listShots.add(ps);
                    }
                }
                timeShot = 0.0;
            }
        }
    }

    public void shoot() {
        // Movimentando o disparo na tela (atirando)
        for (int i = 0; i < listShots.size(); i++) {
            PlayerShot ps = listShots.get(i);
            ps.move();
        }
    }

    public void restartShots() {
        while (!this.listShots.isEmpty()) {
            this.stackShots.push(this.listShots.remove(0));
        }
    }

    public void restart() {
        this.life = 3;
        this.points = 0;
        this.posX = 20;
        this.posY = Constants.WINDOW_HEIGHT / 2 - this.height;
    }

    public void renderShots(Graphics g) {
        for (int i = 0; i < this.listShots.size(); i++) {
            this.listShots.get(i).render(g);
        }
    }

    public void render(Graphics g) {
        // g.setColor(new Color(200, 200, 200, 200));
        // g.fillRect((int) this.posX, (int) this.posY, (int) this.width, (int) this.height);
        g.drawImage(sprite, (int) this.posX - 5, (int) this.posY, null);
    }
}
