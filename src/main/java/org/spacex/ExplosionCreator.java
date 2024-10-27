package org.spacex;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

/*
    Every class that implements this interface can invoke the explosion
*/
public interface ExplosionCreator {
    void createExplosion(Coordinate2D anchorLocation, double speed, Size size);
}
