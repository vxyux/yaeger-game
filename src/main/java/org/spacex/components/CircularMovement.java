package org.spacex.components;

import com.github.hanyaeger.api.Coordinate2D;

public class CircularMovement implements MovementPattern {
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
        /*
        Math.cos(angle): Geeft de x-component van een eenheidscirkel voor de gegeven hoek.
        Dit varieert tussen -1 en 1. Wanneer de hoek 0 is, is de cosinus 1 (naar rechts wijzend langs de x-as).
        Bij 90 graden (π/2 radialen) is de cosinus 0 (geen x-verplaatsing).
        Bij 180 graden (π radialen) is de cosinus -1 (naar links wijzend langs de x-as).
        */
        double x= centerX + radius * Math.cos(angle);
        double y = centerY + radius * Math.sin(angle);

        return new Coordinate2D(x, y);
    }



}
