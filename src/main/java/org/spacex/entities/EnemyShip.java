package org.spacex.entities;

public class EnemyShip extends Target{
    private String sprite;

    public EnemyShip(int myhealth, int[] mysize, String sprite) {
        super(myhealth, mysize);
        this.sprite = sprite;
    }
}
