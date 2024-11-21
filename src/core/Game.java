package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JPanel;

import entities.Enemy;
import entities.Player;
import manager.BackgroundManager;
import manager.CollisionManager;
import manager.InputManager;
import manager.TerrainManager;
import utils.Constants;

public class Game extends JPanel {
    public GameState currentState = GameState.MENU;

    public Player player;
    public Stack<Enemy> stackEnemies;
    public ArrayList<Enemy> listEnemies;
    public CollisionManager collisionManager;
    public InputManager inputManager;
    public BackgroundManager bg;
    public TerrainManager terrainManager;

    public Game() {

        player = new Player();
        stackEnemies = new Stack<>();
        listEnemies = new ArrayList<>();
        inputManager = new InputManager();
        collisionManager = new CollisionManager();
        bg = new BackgroundManager();
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
        if (currentState == GameState.MENU) {
            // Iniciar o jogo
            if (inputManager.enter) {
                currentState = GameState.GAME;
                inputManager.enter = false;
            
            // Fechar o jogo
            } else if (inputManager.escape) {
                System.exit(0);
            }
        }

        else if (currentState == GameState.GAME) {
            // Pausar o jogo
            if (inputManager.p) {
                currentState = GameState.PAUSE;
                inputManager.p = false;
                inputManager.escape = false;
            }
        }

        else if (currentState == GameState.PAUSE) {
            // Retornar ao jogo
            if (inputManager.p) {
                currentState = GameState.GAME;
                inputManager.p = false;
            }

            // Sair do jogo
            else if (inputManager.escape) {
                System.exit(0);
            }
        }

    }

    public void update() {
        if (currentState == GameState.GAME) {
            bg.moveScenes();
            // bg.reposition();

            // terrainManager.moveTerrains(-2);
            terrainManager.moveTerrain();

            player.updateVelocityPlayer(inputManager);
            player.move();
            spawnEnemies();
            collisionManager.checkColisionPlayerWithWindow(player);
            collisionManager.checkColisionPlayerWithEnemy(player, stackEnemies, listEnemies);
            collisionManager.checkColisionPlayerWithTerrain(player, terrainManager.shapes);

            player.spawnShot(inputManager);
            player.shoot();
            collisionManager.checkColisionPlayerShotsWithWindow(player.stackShots, player.listShots);

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
                            listEnemies.get(listEnemies.size() - 1).width + 250 < Constants.WINDOW_WIDTH;

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

    public void renderMenu(Graphics g) {
        String title = "GAME MENU";
        String playOption = "Press ENTER to Play";
        String exitOption = "Press ESC to Quit";

        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);
        g.setColor(Color.WHITE);

        // Posiciona o título
        FontMetrics fm = g.getFontMetrics(font);
        int xTitle = (Constants.WINDOW_WIDTH - fm.stringWidth(title)) / 2;
        int yTitle = Constants.WINDOW_HEIGHT / 5; // 1/5 da tela de altura
        g.drawString(title, xTitle, yTitle);

        // Posiciona as opções
        int xPlay = (Constants.WINDOW_WIDTH - fm.stringWidth(playOption)) / 2;
        int yPlay = (Constants.WINDOW_HEIGHT - fm.getHeight()) / 2;
        g.drawString(playOption, xPlay, yPlay);

        int xExit = (Constants.WINDOW_WIDTH - fm.stringWidth(exitOption)) / 2;
        int yExit = yPlay + 50; // Espaçamento de 50px abaixo da opção de jogar
        g.drawString(exitOption, xExit, yExit);
    }

    public void renderPause(Graphics g) {
        String message1 = "'P' TO CONTINUE";
        String message2 = "'ESC' TO QUIT";

        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);

        // Obter o FontMetrics para calcular as dimensões do texto
        FontMetrics fm = g.getFontMetrics(font);

        // Calcular a posição para o primeiro texto ('P' TO CONTINUE)
        int x1 = (Constants.WINDOW_WIDTH - fm.stringWidth(message1)) / 2;
        int y1 = (Constants.WINDOW_HEIGHT - fm.getHeight()) / 2;

        // Calcular a posição para o segundo texto ('ESC' TO QUIT)
        int x2 = (Constants.WINDOW_WIDTH - fm.stringWidth(message2)) / 2;
        int y2 = y1 + fm.getHeight() + 10;

        // Escurecer a tela
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT); 

        // Desenhar as mensagens
        g.setColor(Color.WHITE);
        g.drawString(message1, x1, y1);
        g.drawString(message2, x2, y2);
    }

    /*********************************
     *
     * DESENHANDO NA JANELA ↓↓↓
     * 
     *********************************/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.black);

        if (currentState == GameState.MENU) {
            renderMenu(g);
            return;
        }

        bg.render(g);

        terrainManager.renderTerrains(g);

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

        if (currentState == GameState.PAUSE) {
            renderPause(g);
        }
    }
}
