package org.spacex.entities;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.EntitySpawner;
import com.github.hanyaeger.api.media.SoundClip;
import org.spacex.GameScene;
import org.spacex.components.MovementPattern;
import org.spacex.entities.bullet.EnemyBullet;
import org.spacex.entities.bullet.HeroBullet;

import java.util.List;
//import org.spacex.

public class BossShip extends Target implements UpdateExposer, Collider, Collided {
    private final GameScene gameScene;
    private final MovementPattern pattern;
//    private Bullet[] shootingType;

    private static final int bossShipHealth = 5;
    private long lastBulletFiredTime = 0;
    private static final int BULLET_COOLDOWN = 500;
    private final long lastSpawnedTime;

    // is wel handig om de scene te hebben ;)

    public BossShip(Coordinate2D location, String sprite, MovementPattern movementPattern, GameScene gameScene) {
        super(sprite, location, new Size(200, 200), bossShipHealth);
        this.pattern = movementPattern;
        this.gameScene = gameScene;
        this.lastSpawnedTime = System.currentTimeMillis();
        //this.shootingType = shootingType; // could have
    }

    // Explicitly override Update func from yaeger to 'force' our own update.
    @Override
    public void explicitUpdate(long l) {
        // Berekent de volgende plek waar de Boss moet komen.
        Coordinate2D nextPosition = pattern.calculateNextPosition(getAnchorLocation());
        // Beweeg de BossShip volgens het bewegingspatroon
        setAnchorLocation(nextPosition);

        int SPAWN_COOLDOWN = getRandomSpawnCooldown();

        if(System.currentTimeMillis() - lastSpawnedTime > SPAWN_COOLDOWN) {
            fireBullet();
        }
    }
    private void fireBullet(){
        long currentTime = System.currentTimeMillis();

        double centerX = this.getAnchorLocation().getX() + this.getWidth() / 2;
        double centerY = this.getAnchorLocation().getY() + 50 + this.getHeight() / 2;
        Coordinate2D bulletStartPosition = new Coordinate2D(centerX, centerY);

        // Maakt een nieuwe kogel gebasseerd op de BossShip's locatie + cooldown
        if (System.currentTimeMillis() - lastBulletFiredTime >= BULLET_COOLDOWN) {
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
        gameScene.onBossKilled(100);
    }
}
