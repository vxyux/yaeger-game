package org.spacex.core;

import com.github.hanyaeger.api.Coordinate2D;
import org.spacex.entities.enemy.BossShip;
import org.spacex.targetmovement.BackAndForthMovement;
import java.util.Random;

public class BossManager {
    private GameScene gameScene;
    protected boolean bossActive = false;
    protected boolean bossReadyToSpawn = false;

    public BossManager(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    // Called by EnemySpawner to prepare the boss spawn
    public void prepareBossSpawn() {
        bossReadyToSpawn = true;
    }

    public void update() {
        if (!bossActive && bossReadyToSpawn) {
            spawnBoss();
        }
    }

    // Verandert variabelen afkomstig van getEnemySpawner en runt daar diverse methodes
    public void onBossKilled() {
        bossActive = false;
        bossReadyToSpawn = false;
        gameScene.getEnemySpawner().enemiesKilled = 0;
        gameScene.getEnemySpawner().setCurrentWave(gameScene.getEnemySpawner().getCurrentWave() + 1);
        gameScene.getEnemySpawner().spawnEnemyWave(gameScene.getEnemySpawner().getCurrentWave());
        gameScene.onBossKilled(100);
    }

    public void spawnBoss() {
        bossActive = true;
        bossReadyToSpawn = false;

        Random rand = new Random();
        int randomNumber = rand.nextInt(3);

        // defineer een random sprite voor de krachtige vijand
        String bossSprite;
        if (randomNumber == 0) {
            bossSprite = "sprites/spacecraft-symmetry.png";
        } else if (randomNumber == 1) {
            bossSprite = "sprites/carrier_boss.png";
        } else {
            bossSprite = "sprites/dragonboss_ship.png";
        }

        BossShip boss = new BossShip(new Coordinate2D(gameScene.getWidth() / 2, 50), bossSprite, new BackAndForthMovement(4, 1000), gameScene);
        gameScene.addBoss(boss);
    }
}
