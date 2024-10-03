package org.spacex.components;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.userinput.MouseButtonPressedListener;
import com.github.hanyaeger.api.userinput.MouseEnterListener;
import com.github.hanyaeger.api.userinput.MouseExitListener;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.spacex.SpaceShooter;

public class StartButton extends TextEntity implements MouseButtonPressedListener, MouseEnterListener, MouseExitListener {
    private SpaceShooter spaceShooter;

    public StartButton(Coordinate2D initialLocation, SpaceShooter spaceShooter) {
        super(initialLocation,"PLAY GAME");
        this.spaceShooter = spaceShooter;
        setFill(Color.YELLOW);
        setFont(Font.font("Monospaced", FontWeight.BOLD, 40));
    }

    @Override
    public void onMouseButtonPressed(MouseButton mouseButton, Coordinate2D coordinate2D) {
        spaceShooter.setActiveScene(1);
    }

    @Override
    public void onMouseEntered() {
        setFill(Color.LIGHTYELLOW);
        setCursor(Cursor.HAND);
    }

    @Override
    public void onMouseExited() {
        setFill(Color.YELLOW);
        setCursor(Cursor.DEFAULT);
    }
}
