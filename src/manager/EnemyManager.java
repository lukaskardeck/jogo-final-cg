package manager;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;

import entities.Enemy;
import utils.Resource;

public class EnemyManager {
    public final int NUM_ENEMY = 10;
    public final double MIN_RESPAWN_INTERVAL = 0.7;
    public final double MAX_VELOCITY_ENEMY = 7;
    public final double TIME_DECREMENT_INTERVAL_RESPAWN = 8.0;

    public Stack<Enemy> stackEnemies;
    public ArrayList<Enemy> listEnemies;

    public double timerRespawnEnemy;
    public double intervalRespawn;
    public double elapsedTime;

    public double newVelEnemy = Enemy.ENEMY_VELX_MODULE;

    public EnemyManager() {
        intervalRespawn = 4.0;
        this.stackEnemies = new Stack<>();
        this.listEnemies = new ArrayList<>();

        for (int i = 0; i < NUM_ENEMY; i++) {
            stackEnemies.push(new Enemy());
        }
        listEnemies.add(stackEnemies.pop());
    }

    public void spawnEnemies() {
        timerRespawnEnemy += Resource.getInstance().deltaTime;
        elapsedTime += Resource.getInstance().deltaTime;

        // Verifique se é hora de reduzir o intervalo de respawn
        if (elapsedTime >= TIME_DECREMENT_INTERVAL_RESPAWN) {
            elapsedTime = 0.0; // Resete o contador de 10 segundos
            intervalRespawn = Math.max(intervalRespawn - 0.5, MIN_RESPAWN_INTERVAL);
            // intervalRespawn -= 0.5;
            newVelEnemy = Math.min(newVelEnemy + 0.2, MAX_VELOCITY_ENEMY);
            // System.out.println("Novo intervalo de respawn: " + intervalRespawn);
        }

        if (!stackEnemies.isEmpty()) {
            // Condição para adicionar o primeiro inimigo ou espaçar o próximo inimigo
            boolean shouldSpawnEnemy = (Resource.getInstance().timeGame == 0.0) ||
                    (timerRespawnEnemy >= intervalRespawn);

            if (shouldSpawnEnemy) {
                Enemy e = stackEnemies.pop();
                e.respawn();
                e.velX = -newVelEnemy;
                listEnemies.add(e);
                timerRespawnEnemy = 0.0;
            }
        }

        for (int i = 0; i < listEnemies.size(); i++) {
            listEnemies.get(i).move();
        }
    }

    public void restart() {
        while (!listEnemies.isEmpty()) {
            stackEnemies.push(listEnemies.remove(0));
        }

        intervalRespawn = 4.0;
        newVelEnemy = Enemy.ENEMY_VELX_MODULE;
    }

    public void render(Graphics g) {
        for (int i = 0; i < listEnemies.size(); i++) {
            listEnemies.get(i).render(g);
        }
    }
}
