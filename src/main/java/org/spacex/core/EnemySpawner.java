package org.spacex.core;

import com.github.hanyaeger.api.Coordinate2D;
import org.spacex.entities.enemy.EnemyShip;
import org.spacex.entities.enemy.Target;
import org.spacex.utils.PositionManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class EnemySpawner {
    private GameScene gameScene;
    private Queue<Coordinate2D> enemySpawnQueue = new LinkedList<>();
    private List<Coordinate2D> availablePositions;
    private List<Coordinate2D> usedPositions;
    protected int currentWave = 1;
    protected int enemiesKilled = 0;
    private final int MAX_ENEMIES_ON_SCREEN = 5;
    private int activeEnemyCount = 0;
    private final double minDistance = 150;
    private Random random = new Random();

    public EnemySpawner(GameScene gameScene) {
        this.gameScene = gameScene;
        this.availablePositions = new ArrayList<>();
        this.usedPositions = new ArrayList<>();
    }

    public void spawnInitialWave() {
        spawnEnemyWave(getCurrentWave());
    }

    public void checkSpawnQueue() {
        while (!enemySpawnQueue.isEmpty() && activeEnemyCount < MAX_ENEMIES_ON_SCREEN) {
            spawnEnemy(enemySpawnQueue.poll());
        }
    }

    public void onEnemyKilled() {
        enemiesKilled++;
        activeEnemyCount--;
        // if the Boss is not active, and the Boss is not gonna spawn yet, and 'enemiesKilled' is lower than currentWave * 2;
        if (!gameScene.getBossManager().bossActive && !gameScene.getBossManager().bossReadyToSpawn && enemiesKilled >= getCurrentWave() * 2) {
            if (getCurrentWave() % 10 == 0) { // checks if the wave is in factor 10
                if (activeEnemyCount == 0) {
                    System.out.println("IM GOING TO SPAWN THE BOSS, MUHAHAHA!!!");
                    gameScene.getBossManager().bossReadyToSpawn = true;  // Ready to spawn boss when field is clear
                }
                // else
            } else {
                setCurrentWave(getCurrentWave() + 1);
                spawnEnemyWave(getCurrentWave());
            }
        }

        // Spawn boss if conditions are met
        if (gameScene.getBossManager().bossReadyToSpawn && activeEnemyCount == 0) {
            gameScene.getBossManager().spawnBoss();
        }
    }

    public void generatePossiblePositions() {
        PositionManager.generatePossiblePositions(availablePositions, usedPositions, gameScene.getWidth(), minDistance);
    }

    protected void spawnEnemyWave(int waveNumber) {
        int enemyCount = waveNumber * 2;
        for (int i = 0; i < enemyCount; i++) {
            Coordinate2D position = PositionManager.getValidPosition(availablePositions, usedPositions, random);
            if (activeEnemyCount < MAX_ENEMIES_ON_SCREEN) {
                spawnEnemy(position);
            } else {
                enemySpawnQueue.add(position);
            }
        }
    }

    private void spawnEnemy(Coordinate2D position) {
        Target enemy = new EnemyShip(position, gameScene);
        gameScene.addEnemy(enemy);
        activeEnemyCount++;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int currentWave) {
        this.currentWave = currentWave;
    }
}
