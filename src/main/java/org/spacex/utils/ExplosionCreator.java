package org.spacex.utils;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

public interface ExplosionCreator {
    void createExplosion(Coordinate2D anchorLocation, double speed, Size size);
}
