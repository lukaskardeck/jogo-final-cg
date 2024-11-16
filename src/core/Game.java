package core;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JPanel;

import entities.Background;
import entities.Enemy;
import entities.Player;
import manager.CollisionManager;
import manager.InputManager;
import utils.Constants;

public class Game extends JPanel {

    public Player player;
    public Stack<Enemy> stackEnemies;
    public ArrayList<Enemy> listEnemies;
    public CollisionManager collisionManager;
    public InputManager inputManager;
    public Background bg;

    public Game() {

        player = new Player();
        stackEnemies = new Stack<>();
        listEnemies = new ArrayList<>();
        inputManager = new InputManager();
        collisionManager = new CollisionManager();
        bg = new Background();

        for (int i = 0; i < Constants.NUM_ENEMY; i++) {
            stackEnemies.push(new Enemy());
        }
        listEnemies.add(stackEnemies.pop());

        setFocusable(true);
        setLayout(null);

        addKeyListener(inputManager);

        new Thread(() -> gameloop()).start();
    }

    /*********************************
     *
     * GAMELOOP ↓↓↓
     * 
     *********************************/
    public void handlerEvents() {
    }

    public void update() {
        bg.moveScenes();
        bg.reposition();

        player.updateVelocityPlayer(inputManager);
        player.move();
        collisionManager.checkColisionPlayerWithWindow(player);
        collisionManager.checkColisionPlayerWithEnemy(player, stackEnemies, listEnemies);

        player.shoot(inputManager);
        collisionManager.checkColisionPlayerShotsWithWindow(player.stackShots, player.listShots);

        spawnEnemies();
        collisionManager.checkColisionEnemyWithLeftWindow(stackEnemies, listEnemies);
        collisionManager.checkColisionPlayerShotsWithEnemy(
                player.stackShots,
                player.listShots,
                stackEnemies,
                listEnemies);
    }

    public void render() {
        repaint();
    }

    public void gameloop() {
        while (true) {
            handlerEvents();
            update();
            render();
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*********************************
     *
     * MÉTODOS ↓↓↓
     * 
     *********************************/
    public void spawnEnemies() {
        if (!stackEnemies.isEmpty()) {
            // Condição para adicionar o primeiro tiro ou espaçar o próximo tiro
            boolean shouldSpawnEnemy = listEnemies.isEmpty() ||
                    listEnemies.get(listEnemies.size() - 1).posX +
                            listEnemies.get(listEnemies.size() - 1).width + 200 < Constants.WINDOW_WIDTH;

            if (shouldSpawnEnemy) {
                Enemy e = stackEnemies.pop();
                e.respawn();
                listEnemies.add(e);
            }
        }

        for (int i = 0; i < listEnemies.size(); i++) {
            listEnemies.get(i).move();
        }
    }

    /*********************************
     *
     * DESENHANDO NA JANELA ↓↓↓
     * 
     *********************************/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.gray);
        bg.render(g);

        // Desenhando os disparos do jogador (PlayerShot)
        for (int i = 0; i < player.listShots.size(); i++) {
            player.listShots.get(i).render(g);
        }

        // Desenhando o Inimigo
        for (int i = 0; i < listEnemies.size(); i++) {
            listEnemies.get(i).render(g);
        }

        // Desenhando o Player
        player.render(g);
    }
}
