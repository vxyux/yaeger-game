package org.spacex.entities.player;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import org.spacex.core.GameScene;
import org.spacex.entities.bullet.HeroBullet;
import org.spacex.entities.misc.Meteorite;
import org.spacex.ui.element.HealthBar;

import java.util.List;
import java.util.Set;

public class PlayerShip extends DynamicSpriteEntity implements KeyListener, SceneBorderTouchingWatcher, Collided, UpdateExposer {
    private final GameScene gameScene;
    
    private int healthPoints = 5;
    private final HealthBar healthBar = new HealthBar(new Coordinate2D(getSceneWidth() / 2, getSceneHeight() / 2), healthPoints);

    private long lastBulletFiredTime = 0;
    private long lastCollisionTime = 0;

    private final long collisionCooldown = 1600;

    private boolean isHit = false;
    long flickerStartTime = 0;

    // Constructor van Playership
    public PlayerShip(Coordinate2D location, GameScene gameScene) {
        super("gifs/player.gif", location, new Size(70, 70));
        this.gameScene = gameScene;
        gameScene.addHealthBar(healthBar);
    }

    /*
    Checkt of er een rand van de speelscherm wordt geraakt,
    en zo wel voert dit stukje code uit
    */
    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        setSpeed(0);
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
        } if (rightPressed) {
            setCurrentFrameIndex(1);
            setMotion(3, 90d);
        } else if (upPressed) {
            setMotion(3, 180d);
        } else if (downPressed) {
            setMotion(3, 0d);
        }

        // voorkomt dat de speler kan schieten wanneer het is geraakt
        if (spacePressed && healthPoints != 0 && !isHit) {
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

        Maakt gebruik van een currentTime instantie variabele net als bij
        de cooldown van collision cooldown bij onCollision. Anders blijft
        currentTime, maar optellen in verschillende functie aanroepingen.

        Dit maakt de cooldown mogelijk.
    */
    private void fireBullet() {
        long currentTime = System.currentTimeMillis();

        double centerX = this.getAnchorLocation().getX() + this.getWidth() / 2;
        // - 90y omdat anders de bullet de PlayerShip raakt
        double centerY = this.getAnchorLocation().getY() + this.getHeight() - 90;
        Coordinate2D bulletStartPosition = new Coordinate2D(centerX - 28, centerY - 30);

        // cooldown voor het vuren van een Bullet
        int BULLET_COOLDOWN = 600;
        if (currentTime - lastBulletFiredTime >= BULLET_COOLDOWN) {
            // Maakt een nieuwe kogel gebaseerd op de PlayerShip's locatie
            HeroBullet newBullet = new HeroBullet("sprites/laser_beam.png", bulletStartPosition, gameScene, 5, -180d);
            newBullet.setHue(0.90);
            gameScene.addHeroBullet(newBullet);
            lastBulletFiredTime = currentTime;
            new SoundClip("audios/laser.mp3").play();
        }
    }
    /*
        Zorgt ervoor dat wanneer de PlayerShip met een Bullet botst, maar
        als een keer wordt geregistreerd. Anders kan 1 Bullet alle levens weghalen
        van de player. Vandaar de cooldown net zoals in fireBullet().
    */
    @Override
    public void onCollision(List<Collider> colliders) {
        long currentTime = System.currentTimeMillis();
        // is current time greater than collisionCooldown?
        if (currentTime - lastCollisionTime >= collisionCooldown) {
            if(!isHit) {
                SoundClip hit = new SoundClip("audios/hit2.mp3");
                hit.setVolume(0.6);
                hit.play();
                healthPoints--;
                healthBar.setCurrentHealth(healthPoints);
                lastCollisionTime = currentTime;
            }

            for (Collider collider : colliders) {
                if (collider instanceof Meteorite) {
                    ((Meteorite) collider).remove();
                    healthPoints = 0;
                }
            }

            // wanneer de player geen levens meer heeft
            if(healthPoints == 0) {
                explode();
                remove();
                gameScene.showGameOver();
            }
            else {
                // logica voor flickering of korte invincibility
                isHit = true;
                flickerStartTime = currentTime;
            }
        }
    }

    // zorgt voor een explosie
    public void explode() {
        gameScene.createExplosion(getLocationInScene(), 0, new Size(150, 150));
    }

    @Override
    public void explicitUpdate(long l) {
        // defineert knipper duratie dat 200 milisecondes eerder eindigt dan de cooldown
        long FLICKER_DURATION = collisionCooldown - 200;
        long FLICKER_INTERVAL = 100;
        long currentTime = System.currentTimeMillis();
        if (isHit) {
            // berekent hoeveel tijd er is gepasseerd
            long elapsedTime = currentTime - flickerStartTime;
            if (elapsedTime >= FLICKER_DURATION) {
                // verandert boolean naar vals zodat het stopt met knipperen
                isHit = false;
            } else {
                // verandert de zichtbaarheid van het schip afhankelijk van remainder 2
                boolean isVisible = (elapsedTime / FLICKER_INTERVAL) % 2 == 0;
                if (isVisible) {
                    setOpacity(0);
                } else {
                    setOpacity(1);
                }
            }
        }
    }
}