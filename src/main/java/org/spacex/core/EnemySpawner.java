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

    /*
        Terwijl de enemy wachtrij leeg is en de hoeveelheid enemies niet een maximum overschrijft, dan spawn
     */
    public void checkSpawnQueue() {
        while (!enemySpawnQueue.isEmpty() && activeEnemyCount < MAX_ENEMIES_ON_SCREEN) {
            spawnEnemy(enemySpawnQueue.poll());
        }
    }

    public void onEnemyKilled() {
        enemiesKilled++;
        activeEnemyCount--;
        // als de Boss niet actief is, en de Boss nog niet gaat spawnen, en 'enemiesKilled' lager is dan currentWave * 2;
        if (!gameScene.getBossManager().bossActive && !gameScene.getBossManager().bossReadyToSpawn && enemiesKilled >= getCurrentWave() * 2) {
            if (getCurrentWave() % 5 == 0) { // checks if the wave is in factor 10
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

    // Genereer alle mogelijke plekken waar vijanden kunnen spawnen
    public void generatePossiblePositions() {
        PositionManager.generatePossiblePositions(availablePositions, usedPositions, gameScene.getWidth(), minDistance);
    }

    protected void spawnEnemyWave(int waveNumber) {
        int enemyCount = waveNumber * 2;
        double meteoriteSpawnChance = 0.2; // give a 20% chance of spawning a meteorite
        // for the allowed Enemies count, find suitable positions
        for (int i = 0; i < enemyCount; i++) {
            Coordinate2D position = PositionManager.getValidPosition(availablePositions, usedPositions, random);
            if (activeEnemyCount < MAX_ENEMIES_ON_SCREEN) {
                if (random.nextDouble() < meteoriteSpawnChance) {
                    gameScene.spawnMeteorite(position);  // 20% chance to spawn a meteorite
                }
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
