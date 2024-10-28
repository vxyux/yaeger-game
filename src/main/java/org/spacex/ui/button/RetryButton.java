package org.spacex.ui.button;
import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.spacex.core.GameScene;
import org.spacex.core.SpaceShooter;


public class RetryButton extends Button  {
    private final GameScene gameScene;

    public RetryButton(Coordinate2D initialLocation, SpaceShooter spaceShooter, GameScene gameScene) {
        super(initialLocation, spaceShooter, "RETRY GAME", Color.RED);
        this.gameScene = gameScene;
        setFill(Color.LIGHTBLUE);
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
        setFill(Color.BLUE);
        setCursor(Cursor.DEFAULT);
    }
}
