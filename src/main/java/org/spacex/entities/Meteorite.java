package org.spacex.entities;

public class Meteorite extends Target {
    private String sprite;

    public Meteorite(int myhealth, int[] mysize, String sprite) {
        super(myhealth, mysize);
        this.sprite = sprite;
    }
}
