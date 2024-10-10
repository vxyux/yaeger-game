package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

public class Meteorite extends Target {
    private String sprite;

    public Meteorite(Coordinate2D location) {
        super("sprites/meteorite.png", location, new Size(90, 90));
        this.sprite = sprite;
    }
}
