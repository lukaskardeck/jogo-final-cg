package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;

import manager.InputManager;
import utils.Constants;

public class Player {
    public final int NUM_SHOTS = 20;

    public float width;
    public float height;
    public float posX;
    public float posY;
    public float velX;
    public float velY;

    public Stack<PlayerShot> stackShots;
    public ArrayList<PlayerShot> listShots;

    public BufferedImage sprite;

    public Player() {
        this.posX = 100;
        this.posY = 100;
        this.width = 100;
        this.height = 49;

        this.stackShots = new Stack<>();
        for (int i = 0; i < Constants.NUM_SHOTS; i++) {
            stackShots.push(new PlayerShot());
        }
        this.listShots = new ArrayList<>();

        try {
            this.sprite = ImageIO.read(getClass().getResource("../assets/images/nave_player.png"));
        } catch (Exception e) {
            System.out.println("Erro ao carregar sprite do player");
        }
    }


    public void move() {
        this.posX += this.velX;
        this.posY += this.velY;
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


    public void shoot(InputManager inputHandler) {
        // 'spawn' do tiro no game
        if (inputHandler.shot && !stackShots.isEmpty()) {
            // Condição para adicionar o primeiro tiro ou espaçar o próximo tiro
            if (listShots.isEmpty() || listShots.get(listShots.size() - 1).posX > (this.posX + this.width + 50)) {
                PlayerShot ps = stackShots.pop();
                ps.respawn(this);
                listShots.add(ps);
            }
        }

        // Movimentando o disparo na tela (atirando)
        for (int i = 0; i < listShots.size(); i++) {
            PlayerShot ps = listShots.get(i);
            ps.move();
        }
    }


    public void render(Graphics g) {
        //g.setColor(Color.green);
        //g.fillRect((int) this.posX, (int) this.posY, (int) this.width, (int) this.height);
        g.drawImage(sprite, (int) this.posX, (int) this.posY, null);
    }
}
