package org.spacex;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TitleScene extends StaticScene {
    private SpaceShooter spaceShooter;

    public TitleScene(SpaceShooter spaceShooter) {
        this.spaceShooter = spaceShooter;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/space.png");
    }

    @Override
    public void setupEntities() {
        var titleText = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() / 2),
                "SubSpace"
        );
        titleText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        titleText.setFill(Color.LIGHTBLUE);
        titleText.setFont(Font.font("Sans-serif", FontWeight.BOLD, 80));
        addEntity(titleText);
    }
}
