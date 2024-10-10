package org.spacex.components;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.spacex.SpaceShooter;

public class QuitButton extends Button {
    private SpaceShooter spaceShooter;
    public QuitButton(Coordinate2D initialLocation, SpaceShooter spaceShooter) {
        super(initialLocation, spaceShooter, "ゲームを終了する", Color.RED);
        this.spaceShooter = spaceShooter;
    }

    @Override
    public void onMouseButtonPressed(MouseButton mouseButton, Coordinate2D coordinate2D) {
        spaceShooter.quit();
    }


}
