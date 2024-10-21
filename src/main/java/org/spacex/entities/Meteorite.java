package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.scenes.SceneBorder;
import org.spacex.GameScene;

// extends Target nu tijdelijk weg gehaald
public class Meteorite extends Target implements Collider {
    private final GameScene gameScene;
    private String sprite;

    public Meteorite(Coordinate2D location, GameScene gameScene) {
        super("sprites/meteorite.png", location, new Size(90, 90), 1);
        this.gameScene = gameScene;
        this.sprite = "sprites/meteorite.png";

        setMotion(3, 0d);
    }

    public void explode() {
        gameScene.createExplosion(getLocationInScene(), 0, new Size(150, 150));
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        explode();
        remove();
    }

}
