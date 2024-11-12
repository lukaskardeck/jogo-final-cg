import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Game extends JPanel {

    public Player player;
    public PlayerShot playerShot;
    public Enemy enemy;

    // DIRECIONAIS DE MOVIMENTAÇÃO DO PLAYER
    public boolean key_player_up;
    public boolean key_player_right;
    public boolean key_player_down;
    public boolean key_player_left;

    // FLAG DE DISPARO DO PLAYERSHOT
    public boolean shot;
    public boolean releaseShot;

    // FLAG DE VISIBILIDADE DO INIMIGO
    public boolean enemyDead;

    public Game() {
        player = new Player();
        playerShot = player.playerShot;
        enemy = new Enemy();

        setFocusable(true);
        setLayout(null);

        // ESCUTAR O TECLADO
        addKeyListener(new KeyAdapter() {
            /* QUANDO A TECLA FOR PRESSIONADA */
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> key_player_up = true;
                    case KeyEvent.VK_A -> key_player_left = true;
                    case KeyEvent.VK_S -> key_player_down = true;
                    case KeyEvent.VK_D -> key_player_right = true;
                    case KeyEvent.VK_SPACE -> {
                        shot = true;
                        releaseShot = false;
                    }
                }
            }

            /* QUANDO A TECLA FOR SOLTA */
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> key_player_up = false;
                    case KeyEvent.VK_A -> key_player_left = false;
                    case KeyEvent.VK_S -> key_player_down = false;
                    case KeyEvent.VK_D -> key_player_right = false;
                    case KeyEvent.VK_SPACE -> releaseShot = true;
                }
            }
        });

        new Thread(() -> gameloop()).start();
    }

    /*********************************
     *
     * GAMELOOP ↓↓↓
     * 
     *********************************/
    public void handlerEvents() {
        listenPlayerMovements();
        listenPlayerShotMovements();
    }

    public void update() {
        // métodos do player
        movePlayer();
        colisionPlayerWithWindow();

        // métodos do playerShot
        colisionPlayerShotWithWindow();
        colisionPlayerShotWithEnemy();
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
     * MÉTODOS DO PLAYER ↓↓↓
     * 
     *********************************/
    public void listenPlayerMovements() {
        int velModulePlayer = 3;
        player.velX = player.velY = 0;

        /* MOVIMENTAÇÃO DO PLAYER COM O TECLADO */
        if (key_player_up) {
            player.velY = -velModulePlayer;
            if (key_player_right)
                player.velX = velModulePlayer;
            else if (key_player_left)
                player.velX = -velModulePlayer;
        } else if (key_player_down) {
            player.velY = velModulePlayer;
            if (key_player_right)
                player.velX = velModulePlayer;
            else if (key_player_left)
                player.velX = -velModulePlayer;
        } else if (key_player_right)
            player.velX = velModulePlayer;
        else if (key_player_left)
            player.velX = -velModulePlayer;
    }

    public void movePlayer() {
        player.posX += player.velX;
        player.posY += player.velY;
        movePlayerShot();
    }

    public void colisionPlayerWithWindow() {
        // Colisão com a parte de cima da janela
        if (player.posY < 0) {
            player.posY = 0;
            player.velY = 0;
            playerShot.posY = player.posY + (player.height / 2) - (playerShot.height / 2);
        }

        // Colisão com a parte de baixo da janela
        else if (player.posY + player.height > Principal.WINDOW_HEIGHT) {
            player.posY = Principal.WINDOW_HEIGHT - player.height;
            player.velY = 0;
            playerShot.posY = player.posY + (player.height / 2) - (playerShot.height / 2);
        }

        // Colisão com a lateral esquerda da janela
        if (player.posX < 0) {
            player.posX = 0;
            player.velX = 0;
            playerShot.posX = player.posX + player.width - playerShot.width;
        }

        // Colisão com a lateral direita da janela
        else if (player.posX + player.width > Principal.WINDOW_WIDTH) {
            player.posX = Principal.WINDOW_WIDTH - player.width;
            player.velX = 0;
            playerShot.posX = player.posX + player.width - playerShot.width;
        }
    }

    /*********************************
     *
     * MÉTODOS DO PLAYERSHOT ↓↓↓
     * 
     *********************************/
    public void listenPlayerShotMovements() {
        if (shot) {
            playerShot.velX = 20;
            playerShot.velY = 0;
        } else {
            playerShot.velX = player.velX;
            playerShot.velY = player.velY;
        }
    }

    public void movePlayerShot() {
        playerShot.posX += playerShot.velX;
        playerShot.posY += playerShot.velY;
    }

    public void colisionPlayerShotWithWindow() {
        if (playerShot.posX > Principal.WINDOW_WIDTH) {
            playerShot.posX = player.posX + player.width - playerShot.width;
            playerShot.posY = player.posY + (player.height / 2) - (playerShot.height / 2);
            if (releaseShot) {
                shot = false;
            }
        }
    }

    public void colisionPlayerShotWithEnemy() {
        if (
            // Colisão com o lado esquerdo do inimigo
            playerShot.posX + playerShot.width > enemy.posX
            // Colisão com a parte de cima do inimigo
            && playerShot.posY + playerShot.height > enemy.posY
            // Colisão com a parte de baixo do inimigo
            && playerShot.posY < enemy.posY + enemy.height) {
            playerShot.posX = player.posX + player.width - playerShot.width;
            playerShot.posY = player.posY + (player.height / 2) - (playerShot.height / 2);
            enemy.reposition();
            if (releaseShot) {
                shot = false;
            }
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
        setBackground(Color.lightGray);

        // Desenhando o PlayerShot
        g.setColor(Color.BLUE);
        g.fillRect((int) playerShot.posX, (int) playerShot.posY, (int) playerShot.width, (int) playerShot.height);

        // Desenhando o player
        g.setColor(Color.green);
        g.fillRect((int) player.posX, (int) player.posY, (int) player.width, (int) player.height);

        // Desenhando o Inimigo
        g.setColor(Color.red);
        g.fillRect((int) enemy.posX, (int) enemy.posY, (int) enemy.width, (int) enemy.height);
    }
}
