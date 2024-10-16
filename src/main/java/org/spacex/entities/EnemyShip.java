package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.media.SoundClip;
import org.spacex.GameScene;

import java.util.List;

public class EnemyShip extends Target implements Collider, Collided, UpdateExposer {
    private GameScene gameScene;

    private long lastBulletFiredTime = 0;
    private static final int BULLET_COOLDOWN = 2000;

    public EnemyShip(Coordinate2D location, GameScene gameScene) {
        super("sprites/enemyship.png", location, new Size(110, 110));
        this.gameScene = gameScene;
    }

    @Override
    // checks for collision
    public void onCollision(List<Collider> list) {
        // checks all the collisions that are made
       for (Collider collider : list) {
           if (collider instanceof HeroBullet) {
               // if a collision is made with a Bullet, then:
               gameScene.onEnemyKilled();
               // remove the Bullet
               ((HeroBullet) collider).remove();
               // remove self
               remove();
               // boom!
               explode();
           }
       }
    }

    // Explicitly override Update func from yaeger to 'force' our own update.
    @Override
    public void explicitUpdate(long l) {
        // Zorg dat de Enemy elke seconde een kogel afvuurt
        if (System.currentTimeMillis() - lastBulletFiredTime >= BULLET_COOLDOWN) {
            fireBullet();
            lastBulletFiredTime = System.currentTimeMillis();
        }
    }

    private void fireBullet(){
        // logged de de tijd wanneer de kogel is gevuurd.
        long currentTime = System.currentTimeMillis();
        double centerX = this.getAnchorLocation().getX() + this.getWidth() / 3;
        double centerY = this.getAnchorLocation().getY() + 50 + this.getHeight() / 2;
        Coordinate2D bulletStartPosition = new Coordinate2D(centerX, centerY);
        // Maakt een nieuwe kogel gebasseerd op de BossShip's locatie
        EnemyBullet newBullet = new EnemyBullet("sprites/laser_beam.png", bulletStartPosition, gameScene, 4,0);
        gameScene.addEnemyBullet(newBullet);
        lastBulletFiredTime = currentTime;
        SoundClip soundClip = new SoundClip("audios/laser.mp3");
        soundClip.setVolume(0.20);
        soundClip.play();
    }

    public void explode() {
        gameScene.createExplosion(getLocationInScene(), getSpeed(), new Size(150, 150));
    }
}
