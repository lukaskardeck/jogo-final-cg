package core;

import java.awt.Color;
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
import utils.Resource;

public class Game extends JPanel {
    public boolean inGame;
    public double timer;

    public GameState currentState = GameState.MENU;

    public Player player;
    public Stack<Enemy> stackEnemies;
    public ArrayList<Enemy> listEnemies;
    public CollisionManager collisionManager;
    public InputManager inputManager;
    public BackgroundManager bg;
    public TerrainManager terrainManager;

    public GameView gameView;

    // private long currentTime;
    // private long oldTime;
    // private double deltaTime;

    public Game() {
        inGame = true;

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

        gameView = new GameView();

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
        if (currentState == GameState.MENU || currentState == GameState.GAMEOVER) {
            // Iniciar o jogo
            if (inputManager.enter) {
                restartGame();
                currentState = GameState.GAME;
                inputManager.enter = false;

                // Fechar o jogo
            } else if (inputManager.escape) {
                inGame = false;
            }
        }

        else if (currentState == GameState.GAME) {
            timer += Resource.getInstance().deltaTime;
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
                inGame = false;
            }
        }

    }

    public void update() {
        // deltaTime = deltaTime * 60;
        if (currentState == GameState.GAME) {
            // bg.moveScenes();

            terrainManager.moveTerrain();

            player.updateVelocityPlayer(inputManager);
            player.move();
            spawnEnemies();
            collisionManager.checkCollisionPlayerWithWindow(player);
            collisionManager.checkCollisionPlayerWithEnemy(player, stackEnemies, listEnemies);
            collisionManager.checkCollisionPlayerWithTerrain(player, terrainManager.shapes);

            player.spawnShot(inputManager);
            player.shoot();
            collisionManager.checkCollisionPlayerShotsWithWindow(player.stackShots, player.listShots);

            collisionManager.checkCollisionEnemyWithLeftWindow(stackEnemies, listEnemies);
            collisionManager.checkCollisionPlayerShotsWithEnemy(
                    player.stackShots,
                    player.listShots,
                    stackEnemies,
                    listEnemies);

            if (player.life <= 0 || player.posX < 0) {
                currentState = GameState.GAMEOVER;
            }
        }
    }

    public void render() {
        repaint();
    }

    public void gameloop() {
        Resource.getInstance().oldTime = System.nanoTime();
        while (inGame) {
            Resource.getInstance().currentTime = System.nanoTime();
            Resource.getInstance().deltaTime = (Resource.getInstance().currentTime - Resource.getInstance().oldTime)
                    * (1e-9);

            // System.out.println(timer);

            handlerEvents();
            update();
            render();

            Resource.getInstance().oldTime = Resource.getInstance().currentTime;

            try {
                Thread.sleep(Constants.TIME_PER_FRAME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.exit(0);
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

    public void restartGame() {
        timer = 0.0;

        player.life = 3;
        player.posX = 20;
        player.posY = Constants.WINDOW_HEIGHT / 2 - player.height;

        while (!listEnemies.isEmpty()) {
            stackEnemies.push(listEnemies.remove(0));
        }

        while (!player.listShots.isEmpty()) {
            player.stackShots.push(player.listShots.remove(0));
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
        setBackground(Color.black);

        if (currentState == GameState.MENU) {
            gameView.renderMenu(g);
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

        gameView.renderPlayerLife(g, player);
        gameView.renderMunition(g, player);
        gameView.renderTimer(g, timer);

        if (currentState == GameState.PAUSE) {
            gameView.renderPause(g);
        }

        else if (currentState == GameState.GAMEOVER) {
            gameView.renderGameOver(g);
        }
    }
}
