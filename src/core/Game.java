package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JPanel;

import entities.Background;
import entities.Enemy;
import entities.Player;
import manager.CollisionManager;
import manager.InputManager;
import manager.TerrainManager;
import utils.Constants;

public class Game extends JPanel {

    public Player player;
    public Stack<Enemy> stackEnemies;
    public ArrayList<Enemy> listEnemies;
    public CollisionManager collisionManager;
    public InputManager inputManager;
    public Background bg;
    public TerrainManager terrainManager;

    public Game() {

        player = new Player();
        stackEnemies = new Stack<>();
        listEnemies = new ArrayList<>();
        inputManager = new InputManager();
        collisionManager = new CollisionManager();
        bg = new Background();
        terrainManager = new TerrainManager();

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
        player.updateVelocityPlayer(inputManager);
        player.spawnShot(inputManager);
    }

    public void update() {
        if (!inputManager.pause) {
            bg.moveScenes();
            bg.reposition();

            terrainManager.moveTerrains(-3);

            player.move();
            collisionManager.checkColisionPlayerWithWindow(player);
            collisionManager.checkColisionPlayerWithEnemy(player, stackEnemies, listEnemies);

            player.shoot();
            collisionManager.checkColisionPlayerShotsWithWindow(player.stackShots, player.listShots);

            spawnEnemies();
            collisionManager.checkColisionEnemyWithLeftWindow(stackEnemies, listEnemies);
            collisionManager.checkColisionPlayerShotsWithEnemy(
                    player.stackShots,
                    player.listShots,
                    stackEnemies,
                    listEnemies);
        }
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

    public void messagePause(Graphics g) {
        String message = "PRESS 'ESC' TO CONTINUE";
        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);

        // Obter o FontMetrics para calcular as dimensões do texto
        FontMetrics fm = g.getFontMetrics(font);

        // Calcular a posição para centralizar o texto
        int x = (Constants.WINDOW_WIDTH - fm.stringWidth(message)) / 2; // Posição X centralizada
        int y = (Constants.WINDOW_HEIGHT - fm.getHeight()) / 2 + fm.getAscent(); // Posição Y centralizada

        // Escurecer fundo da tela
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        // Desenhar os textos
        g.setColor(Color.WHITE);
        g.drawString(message, x, y);
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

        // terrainManager.renderTerrains(g);

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

        if (inputManager.pause) {
            messagePause(g);
            return;
        }
    }
}
