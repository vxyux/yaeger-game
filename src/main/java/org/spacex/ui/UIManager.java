package org.spacex.ui;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.spacex.core.SpaceShooter;
import org.spacex.core.GameScene;
import org.spacex.ui.button.QuitButton;
import org.spacex.ui.button.RetryButton;

public class UIManager {
    private GameScene gameScene;
    private SpaceShooter spaceShooter;

    public UIManager(GameScene gameScene, SpaceShooter spaceShooter) {
        this.gameScene = gameScene;
        this.spaceShooter = spaceShooter;
    }

    public void displayGameOverScreen() {
        TextEntity titleText = new TextEntity(new Coordinate2D(gameScene.getWidth() / 2, gameScene.getHeight() / 2), "GAME OVER");
        titleText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        titleText.setFill(Color.RED);
        titleText.setFont(Font.font("Monospaced", FontWeight.BOLD, 80));
        gameScene.addnewEntity(titleText);

        RetryButton retryButton = new RetryButton(new Coordinate2D(gameScene.getWidth() / 2, (gameScene.getHeight() / 2) + 120), spaceShooter, gameScene);
        retryButton.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        gameScene.addnewEntity(retryButton);

        QuitButton quitButton = new QuitButton(new Coordinate2D(gameScene.getWidth() / 2, (gameScene.getHeight() / 2) + 160), spaceShooter);
        quitButton.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        gameScene.addnewEntity(quitButton);
    }
}
