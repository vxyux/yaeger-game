package org.spacex.components;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import org.spacex.SpaceShooter;
public class StartButton extends Button{
    private SpaceShooter spaceShooter;

    public StartButton(Coordinate2D initialLocation, SpaceShooter spaceShooter) {
        super(initialLocation, spaceShooter, "START GAME", Color.YELLOW);

    }

}
