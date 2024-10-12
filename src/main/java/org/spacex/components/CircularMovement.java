package org.spacex.components;

import com.github.hanyaeger.api.Coordinate2D;

public class CircularMovement {
    private double centerX;
    private double centerY;
    private double radius;
    private double angle = 0;
    private double speed;

    public CircularMovement(double centerX, double centerY, double radius, double speed) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.speed = speed;
    }

    public Coordinate2D calculateNextPosition(Coordinate2D currentPosition) {

        angle += speed;
        double x= centerX + radius * Math.cos(angle);
        double y = centerY + radius * Math.sin(angle);

        return new Coordinate2D(x, y);
    }



}
