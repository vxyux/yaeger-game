package org.spacex.ui.button;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.spacex.core.SpaceShooter;

public class StartButton extends Button {
    private SpaceShooter spaceShooter;

    public StartButton(Coordinate2D initialLocation, SpaceShooter spaceShooter) {
        super(initialLocation, spaceShooter, "START GAME", Color.YELLOW);
        this.spaceShooter = spaceShooter;
    }

    public StartButton(Coordinate2D initialLocation, SpaceShooter spaceShooter, String content) {
        super(initialLocation, spaceShooter, content, Color.YELLOW);
    }

    @Override
    public void onMouseButtonPressed(MouseButton mouseButton, Coordinate2D coordinate2D) {
        spaceShooter.setActiveScene(1);
    }
}
