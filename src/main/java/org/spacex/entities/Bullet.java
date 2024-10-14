package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import org.spacex.GameScene;

import java.util.List;

public class Bullet extends DynamicSpriteEntity implements SceneBorderTouchingWatcher, Collider, Collided {
    private double direction;
    private double speed;
    private GameScene gameScene;

    public Bullet(String resource, Coordinate2D initialLocation, GameScene gameScene,double speed, double direction) {
        super(resource, initialLocation, new Size(100, 100));
        this.gameScene = gameScene;
//        setAnchorLocation(initialLocation);
        // bepaal snelheid en direction
        setMotion(speed, direction);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        remove();
        setSpeed(0);
        explode();
    }

    @Override
    public void onCollision(List<Collider> list) {
        // misschien nog later nodig voor user feedback
        //remove();
        //explode();
    }

    public void explode() {
        gameScene.createExplosion(getLocationInScene(), getSpeed(), new Size(60, 60));
    }
}
