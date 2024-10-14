package org.spacex;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.scenes.DynamicScene;
import org.spacex.components.*;
import org.spacex.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
    GameScene implementeert van ExplosionCreator, een interface. Dat verplicht
    GameScene om een methode te hebben genaamd createExplosion.

    CreateExplosion zorgt ervoor dat de explosie wordt aangemaakt/toegevoegt aan
    de GameScene.
*/
public class GameScene extends DynamicScene implements ExplosionCreator {
    private SpaceShooter spaceShooter;
    private int enemiesKilled = 0;
    private int currentWave = 1;
    private boolean bossActive = false;
    private Random random = new Random();


    public GameScene(SpaceShooter spaceShooter) {
        this.spaceShooter = spaceShooter;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/space.png");
        setBackgroundAudio("audios/boss.mp3");
        setBackgroundAudioVolume(200);
    }

    @Override
    public void setupEntities() {
        spawnInitialWave();
        //  Setup Movement Pattern for Boss Ship /////////////////////////////////////////////////////////////////////////

        // Test Case: Left and Right boss movement
//        MovementPattern backAndForthMovement = new BackAndForthMovement(4, 1000);
        // Test Case: Circular boss movement
//        MovementPattern circularMovement = new CircularMovement(getWidth() / 2,50,200,0.01);
        // Test Case: Random movement

        /*
            Maak een nieuwe randomMovement pattern waarbij je de snelheid kan definieren.
         */
//        MovementPattern randommove = new RandomizedMovement(3, 3, 1000,300, 1000);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // hier wordt de PlayerShip gespawnt
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

    // dit is een test functie
    public void update() {
        System.out.println("Scene is updating");
        // Andere logica hier
    }

    private void spawnInitialWave() {
        spawnEnemyWave(currentWave);  // Spawn based on the wave number
    }

    public void onEnemyKilled() {
        enemiesKilled++;

        if (!bossActive) {
            // If all enemies in the wave are killed, start the next wave or boss
            if (enemiesKilled >= currentWave * 5) {
                enemiesKilled = 0;  // Reset for the next wave

                if (currentWave % 5 == 0) {
                    spawnBoss();  // Every 5 waves, spawn a boss
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

    private void spawnEnemyWave(int waveNumber) {
        int enemyCount = waveNumber * 5;  // Scale the number of enemies by wave number
        double minX = 50;
        double maxX = 1000 - 100;
        double minY = 50;
        double maxY = 200;
        double minDistance = 150;  // Minimum distance between enemies

        List<Coordinate2D> spawnedPositions = new ArrayList<>();

        for (int i = 0; i < enemyCount; i++) {
            boolean validPosition = false;
            Coordinate2D position = null;

            while (!validPosition) {
                double targetX = minX + random.nextDouble() * (maxX - minX);
                double targetY = minY + random.nextDouble() * (maxY - minY);
                position = new Coordinate2D(targetX, targetY);

                validPosition = true;
                for (Coordinate2D existingPosition : spawnedPositions) {
                    if (position.distance(existingPosition) < minDistance) {
                        validPosition = false;
                        break;
                    }
                }
            }

            spawnedPositions.add(position);
            Target enemy = new EnemyShip(position, this);
            addEnemy(enemy);
        }
    }

    private void spawnBoss() {
        MovementPattern backAndForthMovement = new BackAndForthMovement(4, 1000);
        bossActive = true;
        Coordinate2D bossPosition = new Coordinate2D(500 - 50, 100);  // Center the boss
        BossShip boss = new BossShip(bossPosition, "sprites/dragonboss_ship.png" ,  backAndForthMovement, this);
        addBoss(boss);
    }

    public void addEnemy(Target enemy) {
        addEntity(enemy);  // Add the enemy to the scene
    }

    public void addBoss(BossShip boss) {
        addEntity(boss);  // Add the boss to the scene
    }
}
