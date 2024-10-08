package org.spacex.entities;

public class BossShip extends Target {
    private String sprite;
    private Bullet[] shootingType; // Bullet has yet to exist
    private int[][] movingPattern;

    public BossShip(int myhealth, int[] mysize, String sprite, Bullet[] shootingType, int[][] movingPattern) {
        super(myhealth, mysize);
        this.sprite = sprite;
        this.shootingType = shootingType;
        this.movingPattern = movingPattern;
    }
}
