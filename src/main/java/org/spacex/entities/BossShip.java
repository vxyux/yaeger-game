package org.spacex.entities;

public class BossShip extends Target {
    private String sprite;
    private Bullet[] shootingType; // Bullet has yet to exist
    private int[][] movingPattern;

    public BossShip(int myHealth, int[] mySize, String sprite, Bullet[] shootingType, int[][] movingPattern) {
        super(myHealth, mySize);
        this.sprite = sprite;
        this.shootingType = shootingType;
        this.movingPattern = movingPattern;
    }
}
