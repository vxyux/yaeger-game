package org.spacex.entities;

public class EnemyShip extends Target{
    private String sprite;

    public EnemyShip(int myHealth, int[] mySize, String sprite) {
        super(myHealth, mySize);
        this.sprite = sprite;
    }
}
