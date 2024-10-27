package org.spacex;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.DynamicScene;
import com.github.hanyaeger.api.UpdateExposer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.spacex.ui.button.QuitButton;
import org.spacex.ui.button.RetryButton;
import org.spacex.targetmovement.BackAndForthMovement;
import org.spacex.targetmovement.CircularMovement;
import org.spacex.targetmovement.MovementPattern;
import org.spacex.targetmovement.RandomizedMovement;
import org.spacex.entities.bullet.EnemyBullet;
import org.spacex.entities.bullet.HeroBullet;
import org.spacex.entities.enemy.BossShip;
import org.spacex.entities.enemy.EnemyShip;
import org.spacex.entities.enemy.Target;
import org.spacex.entities.misc.Explosion;
import org.spacex.entities.player.PlayerShip;
import org.spacex.ui.element.HealthBar;

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
    private boolean bossReadyToSpawn = false; // Track if it's time for the boss
    private Random random = new Random();
    private Queue<Coordinate2D> enemySpawnQueue = new LinkedList<>();
    private final int MAX_ENEMIES_ON_SCREEN = 5;
    private int activeEnemyCount = 0;
    // Pre-defining Boss Movement Patterns:
    private final MovementPattern backAndForthMovement = new BackAndForthMovement(4, 1000);
    private final MovementPattern CircularMovement = new CircularMovement(500,50,200,0.01);
    private final MovementPattern RandomMovement = new RandomizedMovement(3, 3, 1000,300, 1000);

    private Coordinate2D bossPosition = new Coordinate2D(500 - 50, 100);

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

        generatePossiblePositions();
    }

    @Override
    public void setupEntities() {
        //spawnInitialWave();

        PlayerShip player = new PlayerShip(new Coordinate2D(getWidth() / 2, getHeight() / 2), this);
        addEntity(player);

        BossShip boss = new BossShip(new Coordinate2D(getWidth() / 2, 50), "sprites/dragonboss_ship.png" ,  backAndForthMovement, this);
        addEntity(boss);
    }

    public void createExplosion(Coordinate2D anchorLocation, double speed, Size explosionSize) {
        addEntity(new Explosion(anchorLocation, speed, explosionSize));
    }

    private void spawnInitialWave() {
        spawnEnemyWave(currentWave);
    }

    /*
     checks if there are still enemies to be spawned in the queue
    */
    public void checkSpawnQueue() {
        while (!enemySpawnQueue.isEmpty() && activeEnemyCount < MAX_ENEMIES_ON_SCREEN && !bossActive && !bossReadyToSpawn) {
            Coordinate2D position = enemySpawnQueue.poll();
            // if there are enemy spots free, spawn enemy.
            spawnEnemy(position);
        }
    }

    private void spawnEnemy(Coordinate2D position) {
        Target enemy = new EnemyShip(position, this);
        addEnemy(enemy);
        activeEnemyCount++;
    }

    @Override
    public void explicitUpdate(long timestamp) {
        if (!bossActive && !bossReadyToSpawn) {
            checkSpawnQueue();
        }
    }
    // wanneer een Enemy wordt vermoord, wordt dit stukje code uitgevoerd.
    public void onEnemyKilled() {
        enemiesKilled++;
        activeEnemyCount--;
        // if the Boss is not active, and the Boss is not gonna spawn yet, and 'enemiesKilled' is lower than currentWave * 2;
        if (!bossActive && !bossReadyToSpawn && enemiesKilled >= currentWave * 2) {
            if (currentWave % 10 == 0) { // checks if the wave is in factor 10
                if (activeEnemyCount == 0) {
                    bossReadyToSpawn = true;  // Ready to spawn boss when field is clear
                }
            // else
            } else {
                currentWave++;
                spawnEnemyWave(currentWave);
            }
        }

        // Spawn boss if conditions are met
        if (bossReadyToSpawn && activeEnemyCount == 0) {
            spawnBoss();
        }
    }

    // if the boss dies..
    public void onBossKilled() {
        bossActive = false;
        bossReadyToSpawn = false;
        enemiesKilled = 0;
        currentWave++;
        spawnEnemyWave(currentWave);
    }

    /*
    Create a ArrayList of locations where the enemies can safely spawn.
    */
    private void generatePossiblePositions() {
        availablePositions = new ArrayList<>();
        usedPositions = new ArrayList<>();
        double minX = 50;
        double maxX = getWidth() - 100;
        double minY = 50;
        double maxY = 200;

        for (double x = minX; x <= maxX; x += minDistance) {
            for (double y = minY; y <= maxY; y += minDistance) {
                availablePositions.add(new Coordinate2D(x, y));
            }
        }
    }
    // searches for a valid Position that has not yet been taken.
    private Coordinate2D getValidPosition() {
        // if all the available positions have been taken, reset the list
        if (availablePositions.isEmpty()) {
            resetPositions();
        }
        // get a random positions from the availablePositions ArrayList, and set it as the Position
        int index = random.nextInt(availablePositions.size());
        Coordinate2D position = availablePositions.get(index);

        availablePositions.remove(index);
        usedPositions.add(position);
        // return the position
        return position;
    }
    // reset the availablePositions ArrayList
    private void resetPositions() {
        availablePositions.addAll(usedPositions);
        usedPositions.clear();
    }
    // Spawn the (next) Enemy Wave
    private void spawnEnemyWave(int waveNumber) {
        int enemyCount = waveNumber * 2;
        // for the allowed Enemies count, find suitable positions
        for (int i = 0; i < enemyCount; i++) {
            Coordinate2D position = getValidPosition();
            if (activeEnemyCount < MAX_ENEMIES_ON_SCREEN) {
                spawnEnemy(position);
            } else {
                enemySpawnQueue.add(position);
            }
        }
    }

    public void addHealthBar(HealthBar healthBar) {
        addEntity(healthBar);
    }

    // zo kan addEntity aangeroepen worden (met EnemyBullet als parameter)!
    public void addEnemyBullet(EnemyBullet newBullet) {
        newBullet.setAnchorPoint(AnchorPoint.CENTER_LEFT);
        addEntity(newBullet);
    }
    // zo kan addEntity aangeroepen worden (met HeroBullet als parameter)!
    public void addHeroBullet(HeroBullet myBullet) {
        myBullet.setAnchorPoint(AnchorPoint.CENTER_LEFT);
        addEntity(myBullet);
    }
    // Spawn the Boss
    private void spawnBoss() {
        bossActive = true;
        bossReadyToSpawn = false;  // Reset boss spawn flag
        Random rand = new Random();
        int randomNumber = rand.nextInt(5);

        // Make it possible to have a different Boss Spawn in every Boss Round:
        if (randomNumber == 1) { // if randomNumber is 1:
            BossShip boss = new BossShip(new Coordinate2D(getWidth() / 2, 50), "sprites/spacecraft-symmetry.png" ,  CircularMovement, this);
            addEntity(boss);
        }
        if (randomNumber == 2) { // if randomNumber is 2:
            BossShip boss = new BossShip(new Coordinate2D(getWidth() / 2, 50), "sprites/carrier_boss.png" ,RandomMovement, this);
        } else { // if randomNumber is not 1 or 2:
            BossShip boss = new BossShip(new Coordinate2D(getWidth() / 2, 50), "sprites/dragonboss_ship.png", backAndForthMovement, this);
            addBoss(boss);
        }
    }

    // Add Enemy to the Scene
    public void addEnemy(Target enemy) {
        addEntity(enemy);
    }

    // Add Boss to the Scene
    public void addBoss(BossShip boss) {
        addEntity(boss);
    }

    public void showGameOver() {
        var titleText = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() / 2),
                "GAME OVER"
        );
        titleText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        titleText.setFill(Color.RED);
        titleText.setFont(Font.font("Monospaced", FontWeight.BOLD, 80));
        addEntity(titleText);

        var retryButton = new RetryButton(
                new Coordinate2D(getWidth() / 2, (getHeight() / 2) + 120), spaceShooter, this);
        retryButton.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(retryButton);

        var quitButton = new QuitButton(
                new Coordinate2D(getWidth() / 2, (getHeight() / 2) + 160), spaceShooter);
        quitButton.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(quitButton);
    }

    public void restartGame() {
        spaceShooter.restartGame();
    }
}
