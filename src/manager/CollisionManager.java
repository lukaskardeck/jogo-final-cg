package manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import entities.Enemy;
import entities.Player;
import entities.PlayerShot;
import shapes.RectangleShape;
import shapes.Shape;
import utils.Constants;

public class CollisionManager {
    public void checkCollisionPlayerWithWindow(Player player) {
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

    public void checkCollisionPlayerShotsWithEnemy(
            Player player,
            Stack<Enemy> stackEnemies,
            ArrayList<Enemy> listEnemies) {

        for (int i = 0; i < player.listShots.size(); i++) {
            PlayerShot ps = player.listShots.get(i);
            for (int j = 0; j < listEnemies.size(); j++) {
                Enemy e = listEnemies.get(j);
                if (
                // Colisão com o lado esquerdo do inimigo
                ps.posX + ps.width > e.posX
                        // Colisão com a parte de cima do inimigo
                        && ps.posY + ps.height > e.posY
                        // Colisão com a parte de baixo do inimigo
                        && ps.posY < e.posY + e.height
                        && ps.posX < e.posX + e.width) {
                    player.stackShots.push(player.listShots.remove(i));
                    stackEnemies.push(listEnemies.remove(j));
                    player.points += 10;
                }
            }
        }
    }

    public void checkCollisionPlayerShotsWithWindow(
            Stack<PlayerShot> stackShots,
            ArrayList<PlayerShot> listShots) {

        for (int i = 0; i < listShots.size(); i++) {
            PlayerShot ps = listShots.get(i);
            if (ps.posX > Constants.WINDOW_WIDTH) {
                stackShots.push(listShots.remove(i));
            }
        }
    }

    public void checkCollisionPlayerWithEnemy(
            Player player,
            Stack<Enemy> stackEnemies,
            ArrayList<Enemy> listEnemies) {

        for (int i = 0; i < listEnemies.size(); i++) {
            Enemy e = listEnemies.get(i);
            if (player.posX + player.width - 20 > e.posX &&
                    player.posY + player.height - 20 > e.posY &&
                    player.posX + 20 < e.posX + e.width &&
                    player.posY + 20 < e.posY + e.height) {
                stackEnemies.push(listEnemies.remove(i));
                player.life--;
            }
        }
    }

    public void checkCollisionEnemyWithLeftWindow(
            Stack<Enemy> stackEnemies,
            ArrayList<Enemy> listEnemies) {
        for (int i = 0; i < listEnemies.size(); i++) {
            Enemy e = listEnemies.get(i);
            if (e.posX + e.width < 0) {
                stackEnemies.push(listEnemies.remove(i));
            }
        }
    }

    // public void checkColisionPlayerWithTerrain(Player player, ArrayList<Shape>
    // shapes) {
    // for (Shape shape : shapes) {
    // if (shape instanceof RectangleShape) {
    // RectangleShape rect = (RectangleShape) shape;
    // if (rect.posX < Constants.WINDOW_WIDTH) {
    // if (player.posX + player.width > rect.posX &&
    // player.posY + player.height > rect.posY &&
    // player.posX < rect.posX + rect.width &&
    // player.posY < rect.posY + rect.height) {

    // // player.velX += rect.velX;
    // player.velX = 0;

    // }
    // }
    // }
    // }
    // }

    public void checkCollisionPlayerWithTerrain(Player player, ArrayList<Shape> shapes) {
        for (Shape shape : shapes) {
            if (shape instanceof RectangleShape rect) {
                // Verificar colisão apenas se o terreno estiver visível na tela
                if (rect.posX < Constants.WINDOW_WIDTH) {
                    // Verificar colisão
                    boolean isColliding = player.posX + player.width > rect.posX &&
                            player.posY + player.height > rect.posY &&
                            player.posX < rect.posX + rect.width &&
                            player.posY < rect.posY + rect.height;

                    if (isColliding) {
                        // Calcular profundidade da colisão em cada direção
                        double overlapTop = (rect.posY + rect.height) - player.posY; // Superior
                        double overlapBottom = (player.posY + player.height) - rect.posY; // Inferior
                        double overlapLeft = (rect.posX + rect.width) - player.posX; // Lateral esquerda
                        double overlapRight = (player.posX + player.width) - rect.posX; // Lateral direita

                        double[] overLaps = new double[] { overlapTop, overlapBottom, overlapLeft, overlapRight };
                        double minOverlap = Arrays.stream(overLaps).min().getAsDouble();

                        // ↓ Determinar o lado da colisão (o menor overlap indica a direção da colisão) ↓

                        // Colisão com a parte superior do terreno
                        if (minOverlap == overlapTop) {
                            player.posY = rect.posY + rect.height;
                            player.velY = 0;
                        } 
                        
                        // Colisão com a parte inferior do terreno
                        else if (minOverlap == overlapBottom) {
                            player.posY = rect.posY - player.height;
                            player.velY = 0; // Parar movimento para baixo
                        } 
                        
                        // Colisão lateral direita do terreno
                        else if (minOverlap == overlapLeft) {
                            player.posX = rect.posX + rect.width; // Empurrar para a direita
                            player.velX = 0;
                        } 
                        
                        // Colisão lateral esquerda do terreno
                        else if (minOverlap == overlapRight) {
                            player.posX = rect.posX - player.width;
                            player.velX = 0;
                        }

                    }
                }
            }
        }
    }

}
