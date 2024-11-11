import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Game extends JPanel {
    
    public Player player;

    // DIRECIONAIS DE MOVIMENTAÇÃO DO PLAYER
    public boolean key_player_up;
    public boolean key_player_right;
    public boolean key_player_down;
    public boolean key_player_left;

    public Game() {
        player = new Player(100, 50, 100, 100);

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
        movePlayer();
    }


    public void update() {
        // métodos do player
        player.move();
        colisionPlayerWithWindow();

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
    public void movePlayer() {
        int velModulePlayer = 3;
        player.velX = player.velY = 0;

        /* MOVIMENTAÇÃO DO PLAYER COM O TECLADO */
        if (key_player_up) {
            player.velY = -velModulePlayer;
            if (key_player_right) player.velX = velModulePlayer;
            else if (key_player_left) player.velX = -velModulePlayer;
        } 
        else if (key_player_down) {
            player.velY = velModulePlayer;
            if (key_player_right) player.velX = velModulePlayer;
            else if (key_player_left) player.velX = -velModulePlayer;
        }
        else if (key_player_right) player.velX = velModulePlayer;
        else if (key_player_left) player.velX = -velModulePlayer;
    }


    public void colisionPlayerWithWindow() {
        // Colisão com a parte de cima da janela
        if (player.posY <= 0) {
            player.posY = 0;
        }

        // Colisão com a parte de baixo da janela
        else if (player.posY + player.height >= Principal.WINDOW_HEIGHT) {
            player.posY = Principal.WINDOW_HEIGHT - player.height;
        }

        // Colisão com a lateral esquerda da janela
        if (player.posX <= 0) {
            player.posX = 0;
        }

        // Colisão com a lateral direita da janela
        else if (player.posX + player.width >= Principal.WINDOW_WIDTH) {
            player.posX = Principal.WINDOW_WIDTH - player.width;
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
        g.setColor(Color.magenta);
        g.fillRect((int) player.posX, (int) player.posY, (int) player.width, (int) player.height);
    }
}
