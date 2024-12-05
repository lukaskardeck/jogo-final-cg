package core;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import entities.Player;
import manager.BackgroundManager;
import manager.CollisionManager;
import manager.EnemyManager;
import manager.InputManager;
import manager.TerrainManager;
import utils.Constants;
import utils.Resource;

public class Game extends JPanel {
    public boolean inGame;

    public GameState currentState = GameState.MENU;

    public Player player;
    public EnemyManager enemyManager;
    public CollisionManager collisionManager;
    public InputManager inputManager;
    public BackgroundManager bg;
    public TerrainManager terrainManager;

    public GameView gameView;

    private long currentTime;
    private long oldTime;

    public Game() {
        inGame = true;

        player = new Player();
        enemyManager = new EnemyManager();
        inputManager = new InputManager();
        collisionManager = new CollisionManager();
        bg = new BackgroundManager();
        terrainManager = new TerrainManager();

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
            Resource.getInstance().timerGame += Resource.getInstance().deltaTime;
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
            bg.moveScenes();

            terrainManager.moveTerrain();
            // terrainManager.colorTransition();

            player.updateVelocityPlayer(inputManager);
            player.move();
            enemyManager.spawnEnemies();
            collisionManager.checkCollisionPlayerWithEnemy(player, enemyManager.stackEnemies, enemyManager.listEnemies);
            collisionManager.checkCollisionPlayerWithWindow(player);
            collisionManager.checkCollisionPlayerWithTerrain(player, terrainManager.shapes);

            player.spawnShot(inputManager);
            player.shoot();
            collisionManager.checkCollisionPlayerShotsWithWindow(player.stackShots, player.listShots);

            collisionManager.checkCollisionEnemyWithLeftWindow(enemyManager.stackEnemies, enemyManager.listEnemies);
            collisionManager.checkCollisionPlayerShotsWithEnemy(
                    player,
                    enemyManager.stackEnemies,
                    enemyManager.listEnemies);

            if (player.life <= 0 || player.posX < 0) {
                currentState = GameState.GAMEOVER;
            }
        }
    }

    public void render() {
        repaint();
    }

    public void gameloop() {
        oldTime = System.nanoTime();
        while (inGame) {
            currentTime = System.nanoTime();
            Resource.getInstance().deltaTime = (currentTime - oldTime) * (1e-9);

            // System.out.println(timer);

            handlerEvents();
            update();
            render();

            oldTime = currentTime;

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

    public void restartGame() {
        Resource.getInstance().timerGame = 0.0;
        player.restart();
        player.restartShots();
        enemyManager.restart();
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
        player.renderShots(g);
        enemyManager.render(g);
        player.render(g);

        gameView.renderPlayerLife(g, player);
        gameView.renderPoints(g, player);
        gameView.renderTimer(g, Resource.getInstance().timerGame);

        if (currentState == GameState.PAUSE) {
            gameView.renderPause(g);
        }

        else if (currentState == GameState.GAMEOVER) {
            gameView.renderGameOver(g);
        }
    }
}
