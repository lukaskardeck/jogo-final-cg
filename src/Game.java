import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JPanel;

public class Game extends JPanel {

    public Player player;
    public Queue<PlayerShot> playerShotQueue;
    public ArrayList<PlayerShot> playerShot;
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
        playerShotQueue = player.playerShot;
        playerShot = new ArrayList<>();
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
        addPlayerShotsInGame();
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
        int velModulePlayer = 5;
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
        }

        // Colisão com a parte de baixo da janela
        else if (player.posY + player.height > Principal.WINDOW_HEIGHT) {
            player.posY = Principal.WINDOW_HEIGHT - player.height;
            player.velY = 0;
        }

        // Colisão com a lateral esquerda da janela
        if (player.posX < 0) {
            player.posX = 0;
            player.velX = 0;
        }

        // Colisão com a lateral direita da janela
        else if (player.posX + player.width > Principal.WINDOW_WIDTH) {
            player.posX = Principal.WINDOW_WIDTH - player.width;
            player.velX = 0;
        }
    }

    /*********************************
     *
     * MÉTODOS DO PLAYERSHOT ↓↓↓
     * 
     *********************************/
    public void addPlayerShotsInGame() {
        //System.out.println("Queue: " + playerShotQueue.size() + ";   Lista: " + playerShot.size());
        if (shot) {
            if (!playerShotQueue.isEmpty()) {
                if (playerShot.isEmpty()) {
                    PlayerShot ps = playerShotQueue.remove();
                    ps.setPosition(player.posX + player.width - ps.width,
                            player.posY + (player.height / 2) - (ps.height / 2));
                    playerShot.add(ps);
                }

                else if (playerShot.get(playerShot.size() - 1).posX > (player.posX + player.width + 50)) {
                    PlayerShot ps = playerShotQueue.remove();
                    ps.setPosition(player.posX + player.width - ps.width,
                            player.posY + (player.height / 2) - (ps.height / 2));
                    playerShot.add(ps);
                }
            }
        }
    }

    public void listenPlayerShotMovements() {
        if (releaseShot) {
            shot = false;
        }

        for (int i = 0; i < playerShot.size(); i++) {
            PlayerShot ps = playerShot.get(i);

            if (shot) {
                ps.isActive = true;
            }

            if (ps.isActive) {
                ps.velX = 10;
                ps.velY = 0;
            }
        }
    }

    public void movePlayerShot() {
        for (int i = 0; i < playerShot.size(); i++) {
            PlayerShot ps = playerShot.get(i);
            ps.posX += ps.velX;
            ps.posY += ps.velY;
        }
    }

    public void colisionPlayerShotWithWindow() {
        for (int i = 0; i < playerShot.size(); i++) {
            PlayerShot ps = playerShot.get(i);
            if (ps.posX > Principal.WINDOW_WIDTH) {
                ps.isActive = false;
                playerShotQueue.add(playerShot.remove(i));
            }
        }
    }

    public void colisionPlayerShotWithEnemy() {
        for (int i = 0; i < playerShot.size(); i++) {
            PlayerShot ps = playerShot.get(i);
            if (
            // Colisão com o lado esquerdo do inimigo
            ps.posX + ps.width > enemy.posX
                    // Colisão com a parte de cima do inimigo
                    && ps.posY + ps.height > enemy.posY
                    // Colisão com a parte de baixo do inimigo
                    && ps.posY < enemy.posY + enemy.height) {
                ps.isActive = false;
                playerShotQueue.add(playerShot.remove(i));
                enemy.reposition();

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
        for (int i = 0; i < playerShot.size(); i++) {
            PlayerShot ps = playerShot.get(i);
            g.fillRect((int) ps.posX, (int) ps.posY, (int) ps.width, (int) ps.height);
        }

        // Desenhando o Inimigo
        g.setColor(Color.red);
        g.fillRect((int) enemy.posX, (int) enemy.posY, (int) enemy.width, (int) enemy.height);

        // Desenhando o player
        g.setColor(Color.green);
        g.fillRect((int) player.posX, (int) player.posY, (int) player.width, (int) player.height);
    }
}
