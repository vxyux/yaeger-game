package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import org.spacex.GameScene;
import org.spacex.components.ExplosionCreator;

import java.util.List;

public class EnemyShip extends Target implements Collider, Collided {
    private GameScene gameScene;

    public EnemyShip(Coordinate2D location, GameScene gameScene) {
        super("sprites/enemyship.png", location, new Size(110, 110));
        this.gameScene = gameScene;
    }

    @Override
    public void onCollision(List<Collider> list) {
        remove();
        explode();
    }

    public void explode() {
        gameScene.createExplosion(getLocationInScene(), getSpeed(), new Size(150, 150));
    }
}
