package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;

public class Bullet extends DynamicSpriteEntity implements SceneBorderTouchingWatcher, Collider {
    public Bullet(String resource, Coordinate2D initialLocation) {
        super(resource, initialLocation, new Size(100, 100));
        setAnchorLocation(initialLocation);
        // bepaal snelheid en direction
        setMotion(5, -180d);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        remove();
    }
}
