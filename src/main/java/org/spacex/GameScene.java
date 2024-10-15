package org.spacex;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.scenes.DynamicScene;
import com.github.hanyaeger.api.UpdateExposer;
import org.spacex.components.*;
import org.spacex.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.Queue;
import java.util.LinkedList;

public class GameScene extends DynamicScene implements ExplosionCreator, UpdateExposer {
    private SpaceShooter spaceShooter;
    private int enemiesKilled = 0;
    private int currentWave = 1;
    private boolean bossActive = false;
    private Random random = new Random();
    private Queue<Coordinate2D> enemySpawnQueue = new LinkedList<>(); // Queue for deferred enemy spawning
    private final int MAX_ENEMIES_ON_SCREEN = 5;
    private int activeEnemyCount = 0;  // Track active enemies
    private final MovementPattern backAndForthMovement = new BackAndForthMovement(4, 1000);
    private Coordinate2D bossPosition = new Coordinate2D(500 - 50, 100);  // Center the boss

    // Lijst van herbruikbare posities voor vijanden
    private List<Coordinate2D> availablePositions;
    private List<Coordinate2D> usedPositions;
    private final double minDistance = 150;

    public GameScene(SpaceShooter spaceShooter) {
        this.spaceShooter = spaceShooter;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/space.png");
        setBackgroundAudio("audios/boss.mp3");
        setBackgroundAudioVolume(200);

        // Genereer mogelijke vijandlocaties aan het begin van het spel
        generatePossiblePositions();
    }

    @Override
    public void setupEntities() {
        spawnInitialWave();

        // Hier wordt het PlayerShip gespawnd
        PlayerShip player = new PlayerShip(new Coordinate2D(getWidth() / 2, getHeight() / 2), spaceShooter, this);
        addEntity(player);

//        EnemyShip enemy = new EnemyShip(new Coordinate2D(getWidth() / 2, getHeight() / 4), this);
//        addEntity(enemy);

        // Test subject: Boss, nr: 1;
//        BossShip boss = new BossShip(new Coordinate2D(getWidth() / 2, 50), "sprites/dragonboss_ship.png" ,  backAndForthMovement, this);
//        addEntity(boss);

        // Test subject: Boss, nr: 2;
//        BossShip boss2 = new BossShip(new Coordinate2D(getWidth() / 2, 50), "sprites/spacecraft-symmetry.png" ,  randommove, this);
//        addEntity(boss2);


    }

    // zo kan addEntity aangeroepen worden (met Bullet als parameter)!
    public void addBullet(Bullet newBullet) {
        newBullet.setAnchorPoint(AnchorPoint.CENTER_LEFT);
        addEntity(newBullet);
    }

/*
    Voegt een nieuwe explosie sprite toe, afhankelijk van de locatie
    Wordt alleen aangeroepen in andere klassen
*/
    public void createExplosion(Coordinate2D anchorLocation, double speed, Size explosionSize) {
        addEntity(new Explosion(anchorLocation, speed, explosionSize));
    }

    private void spawnInitialWave() {
        spawnEnemyWave(currentWave);  // Spawn based on the wave number
    }

    public void checkSpawnQueue() {
        while (!enemySpawnQueue.isEmpty() && activeEnemyCount < MAX_ENEMIES_ON_SCREEN) {
            Coordinate2D position = enemySpawnQueue.poll();
            spawnEnemy(position);  // Spawn enemy from the queue
        }
    }

    private void spawnEnemy(Coordinate2D position) {
        Target enemy = new EnemyShip(position, this);
        addEnemy(enemy);
        activeEnemyCount++;  // Increment the active enemy counter
    }

    @Override
    public void explicitUpdate(long timestamp) {
        if (!bossActive) {
            checkSpawnQueue();  // Only spawn enemies if boss is not active
        }  // Periodically check the queue to spawn more enemies
    }

    public void onEnemyKilled() {
        enemiesKilled++;
        activeEnemyCount--;  // Decrement active enemy counter when one is killed

        if (!bossActive) {
            // If all enemies in the wave are killed, start the next wave or boss
            if (enemiesKilled >= currentWave * 2) {
                if (currentWave % 10 == 0) {
                    spawnBoss();  // Every 10 waves, spawn a boss
                } else {
                    currentWave++;  // Move to the next wave
                    spawnEnemyWave(currentWave);  // Spawn regular enemies
                }
            }
        }
    }

    public void onBossKilled() {
        bossActive = false;  // Mark boss as defeated
        currentWave++;  // Move to the next wave
        enemiesKilled = 0;  // Reset enemy counter
        spawnEnemyWave(currentWave);  // Spawn the next wave of enemies
    }

    // Genereer mogelijke vijandposities bij de start van het spel
    private void generatePossiblePositions() {
        availablePositions = new ArrayList<>();
        usedPositions = new ArrayList<>();
        double minX = 50;
        double maxX = getWidth() - 100;
        double minY = 50;
        double maxY = 200;

        // Voeg posities toe met voldoende afstand ertussen
        for (double x = minX; x <= maxX; x += minDistance) {
            for (double y = minY; y <= maxY; y += minDistance) {
                availablePositions.add(new Coordinate2D(x, y));
            }
        }
    }

    // Haal een geldige positie uit de beschikbare lijst
    private Coordinate2D getValidPosition() {
        if (availablePositions.isEmpty()) {
            resetPositions();  // Herstel de lijst als alle posities zijn gebruikt
        }

        int index = random.nextInt(availablePositions.size());
        Coordinate2D position = availablePositions.get(index);

        // Verplaats positie van beschikbare naar gebruikte lijst
        availablePositions.remove(index);
        usedPositions.add(position);

        return position;
    }

    // Herstel de beschikbare posities voor een nieuwe ronde
    private void resetPositions() {
        availablePositions.addAll(usedPositions);
        usedPositions.clear();
    }

    // Spawn een vijandengolf
    private void spawnEnemyWave(int waveNumber) {
        int enemyCount = waveNumber * 2;

        for (int i = 0; i < enemyCount; i++) {
            Coordinate2D position = getValidPosition();
            if (activeEnemyCount < MAX_ENEMIES_ON_SCREEN) {
                spawnEnemy(position);
            } else {
                enemySpawnQueue.add(position);  // Voeg toe aan de wachtrij als er geen ruimte is
            }
        }
    }

    private void spawnBoss() {
        bossActive = true;
        BossShip boss = new BossShip(new Coordinate2D(getWidth() / 2, 50), "sprites/dragonboss_ship.png", backAndForthMovement, this);
        addBoss(boss);
    }

    public void addEnemy(Target enemy) {
        addEntity(enemy);  // Voeg vijand toe aan de scene
    }

    public void addBoss(BossShip boss) {
        addEntity(boss);  // Voeg de boss toe aan de scene
    }
}
