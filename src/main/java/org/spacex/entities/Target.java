package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;

public class Target {

    private int health;
    private int[] size;

    public Target(int myHealth, int[] mySize) {
        this.health = myHealth;
        this.size = mySize;
    }
}

