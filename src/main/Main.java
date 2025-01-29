package main;

import java.awt.Dimension;
import javax.swing.JFrame;

import core.Game;
import utils.Constants;

public class Main {
    public JFrame window;
    public Game game;

    public Main() {
        window = new JFrame("ScrambleGame");
        game = new Game();
        game.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        window.getContentPane().add(game);

        // Calcula a posição central da tela para posicionar a janela do jogo
        int x = (Constants.SCREEN_SIZE.width - Constants.WINDOW_WIDTH) / 2;
        int y = ((Constants.SCREEN_SIZE.height - Constants.WINDOW_HEIGHT) / 2) - 30;
        window.setLocation(x, y);

        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Encerrar aplicação ao fechar a janela
        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}