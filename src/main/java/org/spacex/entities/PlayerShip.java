package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.EntitySpawnerContainer;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.EntitySpawner;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import com.github.hanyaeger.core.entities.EntityCollection;
import com.google.inject.Injector;
import javafx.scene.input.KeyCode;
import org.spacex.SpaceShooter;
import org.spacex.GameScene;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerShip extends DynamicSpriteEntity implements KeyListener, SceneBorderTouchingWatcher {
    private SpaceShooter spaceShooter;
    private GameScene gameScene;
    private long lastBulletFiredTime = 0;  // Keeps track of the last time a bullet was fired
    private static final int BULLET_COOLDOWN = 250; // 250 milliseconds = 0.25 seconds

    public PlayerShip(Coordinate2D location, SpaceShooter spaceShooter, GameScene gameScene) {
        super("sprites/playership.png", location, new Size(80, 80));

        this.spaceShooter = spaceShooter;
        this.gameScene = gameScene;
    }

    /*
        Maakt een nieuwe instantie aan van de kogel met de bijbehorende sprite
        Roept functie aan in gameScene zodat addEntity aangeroepen kan worden
     */
    private void fireBullet() {
        // Maakt een nieuwe kogel gebasseerd op de PlayerShip's anchor locatie
        Bullet newBullet = new Bullet("sprites/laser_beam.png", getAnchorLocation());
        gameScene.addBullet(newBullet);  // Add the bullet to the game scene
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        setSpeed(0);

        switch (sceneBorder) {
            case TOP:
                setAnchorLocationY(1);
                break;
            case BOTTOM:
                setAnchorLocationY(getSceneHeight() - getHeight() - 1);
                break;
            case LEFT:
                setAnchorLocationX(1);
                break;
            case RIGHT:
                setAnchorLocationX(getSceneWidth() - getWidth() - 1);
            default:
                break;
        }
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.LEFT)) {
            setCurrentFrameIndex(0);
            setMotion(3, 270d);
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            setCurrentFrameIndex(1);
            setMotion(3, 90d);
        } else if (pressedKeys.contains(KeyCode.UP)) {
            setMotion(3, 180d);
        } else if (pressedKeys.contains(KeyCode.DOWN)) {
            setMotion(3, 0d);
        } else if (pressedKeys.contains(KeyCode.SPACE)) {
            // cooldown functie om spam te voorkomen
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastBulletFiredTime >= BULLET_COOLDOWN) {
                fireBullet();
                lastBulletFiredTime = currentTime;
            }
        } else if (pressedKeys.isEmpty()) {
            setSpeed(0);
        }
    }
}