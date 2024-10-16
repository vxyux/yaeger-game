package org.spacex.entities;
import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.EntitySpawner;
import com.github.hanyaeger.api.media.SoundClip;
import org.spacex.GameScene;
import org.spacex.components.MovementPattern;

import java.util.List;
//import org.spacex.

public class BossShip extends Target implements UpdateExposer, Collider, Collided {
    private EntitySpawner entitySpawner;
    private MovementPattern pattern;
    private String sprite;
//    private Bullet[] shootingType; // Bullet has yet to exist

    private long lastBulletFiredTime = 0;
    private static final int BULLET_COOLDOWN = 500;

    // is wel handig om de scene te hebben  ;)
    private GameScene gameScene;

    public BossShip(Coordinate2D location, String sprite, MovementPattern movementPattern, GameScene myScene) {
        super(sprite, location, new Size(200, 200));
        this.sprite = sprite;
//        this.shootingType = shootingType;
        this.pattern = movementPattern;
        this.gameScene = myScene;
    }

    // Explicitly override Update func from yaeger to 'force' our own update.
    @Override
    public void explicitUpdate(long l) {
        // Print statement om te controleren of update wordt aangeroepen
//        System.out.println("BossShip is being updated.");

        // Berekent de volgende plek waar de Boss moet komen.
        Coordinate2D nextPosition = pattern.calculateNextPosition(getAnchorLocation());
        // Beweeg de BossShip volgens het bewegingspatroon
        setAnchorLocation(nextPosition);

        // Zorg dat de Boss elke seconde een kogel afvuurt
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
        Bullet newBullet = new Bullet("sprites/laser_beam.png", bulletStartPosition, gameScene, 4,0);
        gameScene.addBullet(newBullet);
        lastBulletFiredTime = currentTime;
        SoundClip soundClip = new SoundClip("audios/laser.mp3");
        soundClip.setVolume(0.20);
        soundClip.play();
    }

    @Override
    public void onCollision(List<Collider> list) {
        for (Collider collider : list) {
            if (collider instanceof Bullet) {
                gameScene.onBossKilled();
                ((Bullet) collider).remove();
                remove();
                explode();
            }
        }
    }

    public void explode() {
        gameScene.createExplosion(getLocationInScene(), getSpeed(), new Size(150, 150));
    }



}
