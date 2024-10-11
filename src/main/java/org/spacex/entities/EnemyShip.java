package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;

import java.util.List;

public class EnemyShip extends Target implements Collider, Collided {
    private String sprite;

    public EnemyShip(Coordinate2D location) {
        super("sprites/enemyship.png", location, new Size(110, 110));
        this.sprite = sprite;
    }

    @Override
    public void onCollision(List<Collider> list) {
        remove();
    }
}
