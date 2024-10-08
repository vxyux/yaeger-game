package org.spacex.entities;

public class Meteorite extends Target {
    private String sprite;

    public Meteorite(int myHealth, int[] mySize, String sprite) {
        super(myHealth, mySize);
        this.sprite = sprite;
    }
}
