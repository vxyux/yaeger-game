package org.spacex.entities.misc;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.scenes.SceneBorder;
import org.spacex.core.GameScene;
import org.spacex.entities.enemy.Target;

// extends Target nu tijdelijk weg gehaald
public class Meteorite extends Target implements Collider {
    private final GameScene gameScene;
    private String sprite;

        // Constructor van Meteorite
        public Meteorite(Coordinate2D location, GameScene gameScene) {
            super("sprites/meteorite.png", location, new Size(90, 90), 1);
            this.gameScene = gameScene;
            this.sprite = "sprites/meteorite.png";

            setMotion(3, 0d);
        }
            // Zorgt voor een explosie
            public void explode () {
                gameScene.createExplosion(getLocationInScene(), 0, new Size(150, 150));
            }

            /*
            Checkt of er een rand van de speelscherm wordt geraakt,
            en zo wel voert dit stukje code uit
            */
            @Override
            public void notifyBoundaryTouching (SceneBorder sceneBorder)
            {
                explode();
                remove();
            }

        }