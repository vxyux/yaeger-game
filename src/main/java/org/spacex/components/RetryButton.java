package org.spacex.components;
import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.spacex.GameScene;
import org.spacex.SpaceShooter;


public class RetryButton extends Button  {
    private final GameScene gameScene;

    public RetryButton(Coordinate2D initialLocation, SpaceShooter spaceShooter, GameScene gameScene) {
        super(initialLocation, spaceShooter, "RETRY GAME", Color.RED);
        this.gameScene = gameScene;
        setFill(Color.CYAN);
    }

    @Override
    public void onMouseButtonPressed(MouseButton mouseButton, Coordinate2D coordinate2D) {
        gameScene.restartGame();
    }

    @Override
    public void onMouseEntered() {
        setFill(Color.LIGHTBLUE);
        setCursor(Cursor.HAND);
    }

    @Override
    public void onMouseExited() {
        setFill(Color.CYAN);
        setCursor(Cursor.DEFAULT);
    }
}
