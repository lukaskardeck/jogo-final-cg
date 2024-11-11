import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Principal {
    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 768;

    // Obtém as dimensões da tela do dispositivo
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public JFrame window;
    public Game game;

    public Principal() {
        window = new JFrame("ShadowGame");
        game = new Game();
        game.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        window.getContentPane().add(game);      

        // Calcula a posição central da tela para posicionar a janela do jogo
        int x = (SCREEN_SIZE.width - WINDOW_WIDTH) / 2;
        int y = ((SCREEN_SIZE.height - WINDOW_HEIGHT) / 2) - 30;
        window.setLocation(x, y);

        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Encerrar aplicação ao fechar a janela
        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Principal();
    }
}