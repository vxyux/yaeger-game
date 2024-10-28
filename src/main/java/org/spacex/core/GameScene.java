package org.spacex.core;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.scenes.DynamicScene;
import com.github.hanyaeger.api.UpdateExposer;
import org.spacex.core.SpaceShooter;
import org.spacex.entities.bullet.EnemyBullet;
import org.spacex.entities.bullet.HeroBullet;
import org.spacex.entities.enemy.BossShip;
import org.spacex.entities.misc.Explosion;
import org.spacex.entities.enemy.Target;
import org.spacex.entities.misc.Meteorite;
import org.spacex.entities.player.PlayerShip;
import org.spacex.ui.UIManager;
import org.spacex.ui.element.HealthBar;
import org.spacex.utils.ExplosionCreator;

public class GameScene extends DynamicScene implements ExplosionCreator, UpdateExposer {
    private SpaceShooter spaceShooter;
    private EnemySpawner enemySpawner;
    private BossManager bossManager;
    private UIManager uiManager;

    public GameScene(SpaceShooter spaceShooter) {
        this.spaceShooter = spaceShooter;
        this.enemySpawner = new EnemySpawner(this);
        this.bossManager = new BossManager(this);
        this.uiManager = new UIManager(this, spaceShooter);
    }

    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/space.png");
        setBackgroundAudio("audios/boss.mp3");
        setBackgroundAudioVolume(200);
        enemySpawner.generatePossiblePositions();
    }

    @Override
    public void setupEntities() {
        PlayerShip player = new PlayerShip(new Coordinate2D(getWidth() / 2, getHeight() / 2), this);
        addEntity(player);
        enemySpawner.spawnInitialWave();
    }

    @Override
    public void explicitUpdate(long timestamp) {
        enemySpawner.checkSpawnQueue();
        bossManager.update();
    }

    public void createExplosion(Coordinate2D location, double speed, Size size) {
        addEntity(new Explosion(location, speed, size));
    }

    public void addHealthBar(HealthBar healthBar) {
        addEntity(healthBar);
    }

    // Method to add a generic enemy to the game
    public void addEnemy(Target enemy) {
        addEntity(enemy);
    }

    // Add Boss to the Scene
    public void addBoss(BossShip boss) {
        addEntity(boss);
    }

    // New getter method for BossManager
    public BossManager getBossManager() {
        return bossManager;
    }

    // getter method for EnemySpawner
    public EnemySpawner getEnemySpawner() {
        return enemySpawner;
    }

    // Method to handle enemy death
    public void onEnemyKilled() {
        enemySpawner.onEnemyKilled();
        bossManager.update();
    }

    // Method to add an enemy bullet to the game
    public void addEnemyBullet(EnemyBullet newBullet) {
        newBullet.setAnchorPoint(AnchorPoint.CENTER_LEFT);
        addEntity(newBullet);
    }

    // Method to add a hero bullet to the game scene
    public void addHeroBullet(HeroBullet myBullet) {
        myBullet.setAnchorPoint(AnchorPoint.CENTER_LEFT);
        addEntity(myBullet);
    }

    // Method to add a Meteorite to the game scene
    protected void spawnMeteorite(Coordinate2D position) {
        Meteorite meteorite = new Meteorite(position, this);
        addEntity(meteorite);  // Add meteorite to the scene
    }


    public void showGameOver() {
        uiManager.displayGameOverScreen();
    }

    public void restartGame() {
        spaceShooter.restartGame();
    }

    public void addnewEntity(YaegerEntity entity){
        addEntity(entity);
    }
}
