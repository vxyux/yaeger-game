package org.spacex.components.button;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.spacex.SpaceShooter;

public class QuitButton extends Button {
    private SpaceShooter spaceShooter;
    public QuitButton(Coordinate2D initialLocation, SpaceShooter spaceShooter) {
        super(initialLocation, spaceShooter, "QUIT GAME", Color.RED);
        this.spaceShooter = spaceShooter;
    }

    @Override
    public void onMouseButtonPressed(MouseButton mouseButton, Coordinate2D coordinate2D) {
        spaceShooter.quit();
    }

    @Override
    public void onMouseEntered() {
        setFill(Color.DARKRED);
        setCursor(Cursor.HAND);
    }

    @Override
    public void onMouseExited() {
        setFill(Color.RED);
        setCursor(Cursor.DEFAULT);
    }

}
