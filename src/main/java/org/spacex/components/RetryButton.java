package org.spacex.components;
import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import org.spacex.SpaceShooter;


public class RetryButton extends Button  {
    private SpaceShooter spaceShooter;

    public RetryButton(Coordinate2D initialLocation, SpaceShooter spaceShooter) {
        super(initialLocation, spaceShooter, "KUT GAME", Color.RED);
    }


}
