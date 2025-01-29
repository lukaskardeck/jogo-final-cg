package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.InputStream;

import entities.Player;
import utils.Constants;

public class GameView {

    private Font customFont;

    public GameView() {
        try {
            InputStream is = getClass().getResourceAsStream("../assets/fonts/upheavtt.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            customFont = customFont.deriveFont(Font.BOLD, 30);
        } catch (Exception e) {
            System.out.println("Erro ao carregar a fonte!");
        }
    }

    public void renderPoints(Graphics g, Player player) {
        g.setFont(customFont);
        g.setColor(Color.WHITE);
        String msg = "POINTS: " + player.points;
        int xMsg = Constants.WINDOW_WIDTH - 210;
        int yMsg = 36;
        g.drawString(msg, xMsg, yMsg);
    }

    public void renderPlayerLife(Graphics g, Player player) {
        g.setFont(customFont);
        g.setColor(Color.WHITE);
        String msg = "LIFE: " + player.life;
        int xMsg = 20;
        int yMsg = 36;
        g.drawString(msg, xMsg, yMsg);
    }

    public void renderGameOver(Graphics g) {
        String title = "GAMEOVER";
        String playOption = "'ENTER' TO RESTART";
        String exitOption = "'ESC' TO QUIT";

        // Escurecer a tela
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        g.setFont(customFont);
        g.setColor(Color.WHITE);

        // Posiciona o título
        FontMetrics fm = g.getFontMetrics(customFont);
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

        g.setFont(customFont);

        // Obter o FontMetrics para calcular as dimensões do texto
        FontMetrics fm = g.getFontMetrics(customFont);

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

    public void renderMenu(Graphics g) {
        String title = "GAME MENU";
        String playOption = "'ENTER' TO PLAY";
        String exitOption = "'ESC' TO QUIT";

        g.setFont(customFont);
        g.setColor(Color.WHITE);

        // Posiciona o título
        FontMetrics fm = g.getFontMetrics(customFont);
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

    public void renderTimer(Graphics g, double timer) {
        g.setFont(customFont);
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics(customFont);

        String msg = "TIME: " + (int) timer;
        int xMsg = (Constants.WINDOW_WIDTH - fm.stringWidth(msg)) / 2;
        int yMsg = 36;

        g.drawString(msg, xMsg, yMsg);
    }
}
