package org.spacex.entities.bullet;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import org.spacex.GameScene;

public class EnemyBullet extends DynamicSpriteEntity implements Bullet, SceneBorderTouchingWatcher, Collider {
    private double direction;
    private double speed;
    private GameScene gameScene;

    public EnemyBullet(String resource, Coordinate2D initialLocation, GameScene gameScene,double speed, double direction) {
        super(resource, initialLocation, new Size(100, 100));
        this.gameScene = gameScene;
//        setAnchorLocation(initialLocation);
        // bepaal snelheid en direction
        setMotion(speed, direction);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        remove();
        //setSpeed(0);
        //explode();
    }

    public void explode() {
        gameScene.createExplosion(getLocationInScene(), getSpeed(), new Size(60, 60));
    }
}
