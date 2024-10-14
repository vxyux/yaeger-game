package org.spacex.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import org.spacex.SpaceShooter;
import org.spacex.GameScene;

import java.util.Set;

public class PlayerShip extends DynamicSpriteEntity implements KeyListener, SceneBorderTouchingWatcher {
    private SpaceShooter spaceShooter;
    private GameScene gameScene;

    private long lastBulletFiredTime = 0;
    private static final int BULLET_COOLDOWN = 600;

    public PlayerShip(Coordinate2D location, SpaceShooter spaceShooter, GameScene gameScene) {
        super("sprites/playership.png", location, new Size(70, 70));

        this.spaceShooter = spaceShooter;
        this.gameScene = gameScene;
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        setSpeed(0);
        switch (sceneBorder) {
            case TOP:
                // tp naar beneden (reset)
                setAnchorLocationY(getSceneHeight() + getHeight());
                break;
            case BOTTOM:
                setAnchorLocationY(getSceneHeight() - getHeight());
                break;
            case LEFT:
                setAnchorLocationX(1);
                break;
            case RIGHT:
                setAnchorLocationX(getSceneWidth() - getWidth());
            default:
                break;
        }
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        // iedere input in een variabele gestopt voor betere zicht op de code
        boolean leftPressed = pressedKeys.contains(KeyCode.LEFT);
        boolean rightPressed = pressedKeys.contains(KeyCode.RIGHT);
        boolean upPressed = pressedKeys.contains(KeyCode.UP);
        boolean downPressed = pressedKeys.contains(KeyCode.DOWN);
        boolean spacePressed = pressedKeys.contains(KeyCode.SPACE);

        if (leftPressed) {
            setCurrentFrameIndex(0);
            setMotion(3, 270d);
        } if (rightPressed) {
            setCurrentFrameIndex(1);
            setMotion(3, 90d);
        } else if (upPressed) {
            setMotion(3, 180d);
        } else if (downPressed) {
            setMotion(3, 0d);
        }

        if (spacePressed) {
            fireBullet();
        }

        // kijkt of geen van de pijlen zijn ingedrukt
        if (!leftPressed && !rightPressed && !upPressed && !downPressed) {
            setSpeed(0);
        }
    }

    /*
        Maakt een nieuwe instantie aan van de kogel met de bijbehorende sprite
        Roept functie aan van gameScene zodat addEntity daar aangeroepen kan worden
    */
    private void fireBullet() {
        long currentTime = System.currentTimeMillis();
        // cooldown voor het vuren van een Bullet
        if (currentTime - lastBulletFiredTime >= BULLET_COOLDOWN) {
            // Maakt een nieuwe kogel gebasseerd op de PlayerShip's locatie
            Bullet newBullet = new Bullet("sprites/laser_beam.png", getAnchorLocation(), gameScene, 5, -180d);
            newBullet.setHue(0.90);
            gameScene.addBullet(newBullet);
            lastBulletFiredTime = currentTime;
            new SoundClip("audios/laser.mp3").play();
        }
    }
}