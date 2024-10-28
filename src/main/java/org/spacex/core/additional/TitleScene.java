package org.spacex.core.additional;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.spacex.core.SpaceShooter;
import org.spacex.ui.button.QuitButton;
import org.spacex.ui.button.StartButton;

public class TitleScene extends StaticScene {
    private SpaceShooter spaceShooter;

    public TitleScene(SpaceShooter spaceShooter) {
        this.spaceShooter = spaceShooter;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/space.png");
        setBackgroundAudio("audios/menumusic.mp3");
    }

    @Override
    public void setupEntities() {
        var titleText = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() / 2),
                "ASTRO BLASTER"
        );
        titleText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        titleText.setFill(Color.LIGHTBLUE);
        titleText.setFont(Font.font("Monospaced", FontWeight.BOLD, 80));
        addEntity(titleText);

        var startButton = new StartButton(
                new Coordinate2D(getWidth() / 2, (getHeight() / 2) + 120), spaceShooter);
        startButton.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(startButton);


        var quitButton = new QuitButton(
                new Coordinate2D(getWidth() / 2, (getHeight() / 2) + 160), spaceShooter);
        quitButton.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(quitButton);
    }
}
