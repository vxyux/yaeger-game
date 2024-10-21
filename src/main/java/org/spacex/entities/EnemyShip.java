package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.media.SoundClip;
import org.spacex.GameScene;
import org.spacex.entities.bullet.EnemyBullet;

public class EnemyShip extends Target implements Collider, UpdateExposer {
    private final GameScene gameScene;

    private static final int enemyShipHealth = 1;
    private long lastBulletFiredTime = 0;
    private static final int BULLET_COOLDOWN = 2000;
    private final long lastSpawnedTime;

    public EnemyShip(Coordinate2D location, GameScene gameScene) {
        super("gifs/enemy.gif", location, new Size(110, 110), enemyShipHealth);
        this.gameScene = gameScene;
        // verkrijg tijd wanneer EnemyShip wordt geinstantieerd
        this.lastSpawnedTime = System.currentTimeMillis();
    }

    // Explicitly override Update func from yaeger to 'force' our own update.
    @Override
    public void explicitUpdate(long l) {
        int SPAWN_COOLDOWN = getRandomSpawnCooldown();

        if(System.currentTimeMillis() - lastSpawnedTime > SPAWN_COOLDOWN) {
            fireBullet();
        }
    }

    private void fireBullet(){
        long currentTime = System.currentTimeMillis();

        // Maakt een nieuwe kogel gebasseerd op de EnemyShip's locatie
        double centerX = this.getAnchorLocation().getX() + this.getWidth() / 2;
        double centerY = this.getAnchorLocation().getY() + 50 + this.getHeight() / 2;
        Coordinate2D bulletStartPosition = new Coordinate2D(centerX, centerY);

        // Zorg dat de Enemy elke seconde een kogel afvuurt
        if (System.currentTimeMillis() - lastBulletFiredTime >= BULLET_COOLDOWN) {
            lastBulletFiredTime = System.currentTimeMillis();
            EnemyBullet newBullet = new EnemyBullet("sprites/laser-crop.png", bulletStartPosition, gameScene, 4, 0);
            gameScene.addEnemyBullet(newBullet);
            lastBulletFiredTime = currentTime;
            SoundClip soundClip = new SoundClip("audios/laser.mp3");
            soundClip.setVolume(0.20);
            soundClip.play();
        }
    }

    public void explode() {
        gameScene.createExplosion(getLocationInScene(), getSpeed(), new Size(150, 150));
        gameScene.onEnemyKilled(10);
    }
}
