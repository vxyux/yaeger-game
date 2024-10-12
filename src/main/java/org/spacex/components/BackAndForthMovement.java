package org.spacex.components;

import com.github.hanyaeger.api.Coordinate2D;


public class BackAndForthMovement implements MovementPattern {
    private double speed;
    private boolean movingRight = true;
    private double sceneWidth;

    public BackAndForthMovement(double speed, double sceneWidth) {
        this.speed = speed;
        this.sceneWidth = sceneWidth;
    }

    @Override
    public Coordinate2D calculateNextPosition(Coordinate2D currentPosition) {
        double x = currentPosition.getX();

        if (movingRight) {
            x += speed;
            if (x >= sceneWidth) {
                movingRight = false;
            }

        } else {
            x -= speed;
            if (x <= 0) {
                movingRight = true;
            }
        }
        return new Coordinate2D(x, currentPosition.getY());
    }


}

