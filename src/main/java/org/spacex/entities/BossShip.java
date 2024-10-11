package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

public class BossShip extends Target {
    private String sprite;
    private Bullet[] shootingType; // Bullet has yet to exist
    private int[][] movingPattern;

    public BossShip(Coordinate2D location, int[] mySize, String sprite, Bullet[] shootingType, int[][] movingPattern) {
        super("sprites/enemyship.png", location, new Size(70, 70));
        this.sprite = sprite;
        this.shootingType = shootingType;
        this.movingPattern = movingPattern;
    }
}
