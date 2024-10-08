package org.spacex.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.EntitySpawner;

public class BulletSpawner extends EntitySpawner {
    private final Coordinate2D location;

    public BulletSpawner(Coordinate2D location) {
        super(500);
        this.location = location;
    }

    @Override
    protected void spawnEntities() {
//        var newBullet = new Bullet(location, direction, explosionAdder);
//        newBall.setHue(requestedHue);
//        newBall.setSaturation(requestedSaturation);
        // Set the viewOrder to a value higher that the default to ensure the rockets appear from behind
        // the rocket launcher.
//        newBall.setViewOrder(42);
//        spawn(newBall);

        Bullet playerBeam = new Bullet("sprites/laser_beam.png", new Coordinate2D(600, 800));
        playerBeam.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        spawn(playerBeam);
    }
}
