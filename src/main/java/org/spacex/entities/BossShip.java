package org.spacex.entities;

public class BossShip {
    private String sprite;
    private Bullet[] shootingType;
    private int[][] movingPattern;

    public BossShip(String sprite, Bullet[] shootingType, int[][] movingPattern) {
        this.sprite = sprite;
        this.shootingType = shootingType;
        this.movingPattern = movingPattern;
    }
}
