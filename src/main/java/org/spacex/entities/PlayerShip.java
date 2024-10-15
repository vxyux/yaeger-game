package org.spacex.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import org.spacex.GameScene;
import org.spacex.ui.HealthBar;

import java.util.List;
import java.util.Set;

public class PlayerShip extends DynamicSpriteEntity implements KeyListener, SceneBorderTouchingWatcher, Collided {
    private GameScene gameScene;
    private int healthPoints = 5;
    private HealthBar healthBar = new HealthBar(getAnchorLocation(), healthPoints);

    private long lastBulletFiredTime = 0;
    private static final int BULLET_COOLDOWN = 600;
    private long lastCollisionTime = 0;
    private final long collisionCooldown = 1000;

    public PlayerShip(Coordinate2D location, GameScene gameScene) {
        super("gifs/player.gif", location, new Size(70, 70));

        this.gameScene = gameScene;
        gameScene.addHealthBar(healthBar);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        setSpeed(0);
        healthBar.move(0, 0);
        switch (sceneBorder) {
            case TOP:
                // tp naar beneden (reset)
                setAnchorLocationY(1);
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
            healthBar.move(3, 270);
        } if (rightPressed) {
            setCurrentFrameIndex(1);
            setMotion(3, 90d);
            healthBar.move(3, 90);
        } else if (upPressed) {
            setMotion(3, 180d);
            healthBar.move(3, 180);
        } else if (downPressed) {
            setMotion(3, 0d);
            healthBar.move(3, 0);
        }

        if (spacePressed && healthPoints != 0) {
            fireBullet();
        }

        // kijkt of geen van de pijlen zijn ingedrukt
        if (!leftPressed && !rightPressed && !upPressed && !downPressed) {
            setSpeed(0);
            healthBar.move(0, 0);
        }
    }

    /*
        Maakt een nieuwe instantie aan van de kogel met de bijbehorende sprite
        Roept functie aan van gameScene zodat addEntity daar aangeroepen kan worden

        Maakt gebruik van een currentTime instantie variabele net als bij
        de cooldown van collision cooldown bij onCollision. Anders blijft
        currentTime maar optellen in verschillende functie aanroepingen.

        Dit maakt de cooldown mogelijk.
    */
    private void fireBullet() {
        long currentTime = System.currentTimeMillis();
        double centerX = this.getAnchorLocation().getX() + this.getWidth() / 2;
        double centerY = this.getAnchorLocation().getY() + this.getHeight() / 2;
        Coordinate2D bulletStartPosition = new Coordinate2D(centerX - 28, centerY - 30);
        // cooldown voor het vuren van een Bullet
        if (currentTime - lastBulletFiredTime >= BULLET_COOLDOWN) {
            // Maakt een nieuwe kogel gebasseerd op de PlayerShip's locatie
            Bullet newBullet = new Bullet("sprites/laser_beam.png", bulletStartPosition, gameScene, 5, -180d);
            newBullet.setHue(0.90);
            gameScene.addBullet(newBullet);
            lastBulletFiredTime = currentTime;
            new SoundClip("audios/laser.mp3").play();
        }
    }
    /*
        Zorgt ervoor dat wanneer de PlayerShip met een Bullet botst maar
        als 1x wordt geregistreerd. Anders kan 1 Bullet alle levens weghalen
        van de player. Vandaar de cooldown net zoals in fireBullet().
    */
    @Override
    public void onCollision(List<Collider> colliders) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCollisionTime >= collisionCooldown) {
            healthPoints--;
            healthBar.setCurrentHealth(healthPoints);
            lastCollisionTime = currentTime;

            // wanneer de player geen levens meer heeft doe dit:
            if(healthPoints == 0) {
                explode();
                remove();
            }
        }
    }

    public void explode() {
        gameScene.createExplosion(getLocationInScene(), 0, new Size(150, 150));
    }
}