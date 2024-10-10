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
    private int direction;
    private int speed;
    private GameScene gameScene;

    public Bullet(String resource, Coordinate2D initialLocation, GameScene gameScene) {
        super(resource, initialLocation, new Size(100, 100));
        this.gameScene = gameScene;
        setAnchorLocation(initialLocation);
        // bepaal snelheid en direction
        setMotion(5, -180d);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        remove();
        //gameScene.addExplosion(getAnchorLocation(), 6, 90d);
    }

    @Override
    public void onCollision(List<Collider> list) {
        //remove();
        //gameScene.addExplosion(getAnchorLocation(), 6, 90d);
    }
}
