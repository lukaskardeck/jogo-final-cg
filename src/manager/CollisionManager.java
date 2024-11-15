package manager;

import java.util.ArrayList;
import java.util.Stack;

import entities.Enemy;
import entities.Player;
import entities.PlayerShot;
import utils.Constants;

public class CollisionManager {
    public void checkColisionPlayerWithWindow(Player player) {
        // Colisão com a parte de cima da janela
        if (player.posY < 0) {
            player.posY = 0;
            player.velY = 0;
        }

        // Colisão com a parte de baixo da janela
        else if (player.posY + player.height > Constants.WINDOW_HEIGHT) {
            player.posY = Constants.WINDOW_HEIGHT - player.height;
            player.velY = 0;
        }

        // Colisão com a lateral esquerda da janela
        if (player.posX < 0) {
            player.posX = 0;
            player.velX = 0;
        }

        // Colisão com a lateral direita da janela
        else if (player.posX + player.width > Constants.WINDOW_WIDTH) {
            player.posX = Constants.WINDOW_WIDTH - player.width;
            player.velX = 0;
        }
    }

    public void checkColisionPlayerShotsWithEnemy(
            Stack<PlayerShot> stackShots,
            ArrayList<PlayerShot> listShots,
            Stack<Enemy> stackEnemies,
            ArrayList<Enemy> listEnemies) {

        for (int i = 0; i < listShots.size(); i++) {
            PlayerShot ps = listShots.get(i);
            for (int j = 0; j < listEnemies.size(); j++) {
                Enemy e = listEnemies.get(j);
                if (
                // Colisão com o lado esquerdo do inimigo
                ps.posX + ps.width > e.posX
                        // Colisão com a parte de cima do inimigo
                        && ps.posY + ps.height > e.posY
                        // Colisão com a parte de baixo do inimigo
                        && ps.posY < e.posY + e.height) {
                    stackShots.push(listShots.remove(i));
                    stackEnemies.push(listEnemies.remove(j));
                }
            }
        }
    }

    public void checkColisionPlayerShotsWithWindow(
            Stack<PlayerShot> stackShots,
            ArrayList<PlayerShot> listShots) {

        for (int i = 0; i < listShots.size(); i++) {
            PlayerShot ps = listShots.get(i);
            if (ps.posX > Constants.WINDOW_WIDTH) {
                stackShots.push(listShots.remove(i));
            }
        }
    }

    public void checkColisionPlayerWithEnemy(
            Player player,
            Stack<Enemy> stackEnemies,
            ArrayList<Enemy> listEnemies) {

        for (int i = 0; i < listEnemies.size(); i++) {
            Enemy e = listEnemies.get(i);
            if (player.posX + player.width > e.posX &&
                    player.posY + player.height > e.posY &&
                    player.posX < e.posX + e.width &&
                    player.posY < e.posY + e.height) {
                stackEnemies.push(listEnemies.remove(i));
            }
        }
    }

    public void checkColisionEnemyWithLeftWindow(
        Stack<Enemy> stackEnemies, 
        ArrayList<Enemy> listEnemies) {
        for (int i = 0; i < listEnemies.size(); i++) {
            Enemy e = listEnemies.get(i);
            if (e.posX + e.width < 0) {
                stackEnemies.push(listEnemies.remove(i));  
            }
        }
    }
}
