package org.spacex.targetmovement;

import com.github.hanyaeger.api.Coordinate2D;

public interface MovementPattern {

    Coordinate2D calculateNextPosition(Coordinate2D currentPosition);

}
