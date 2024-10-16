package org.spacex.entities;

import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.scenes.SceneBorder;

import java.util.List;

public interface Bullet {

    public void notifyBoundaryTouching(SceneBorder sceneBorder);

    public void onCollision(List<Collider> list);

    public void explode();

}
