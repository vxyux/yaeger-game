package org.spacex.ui.element;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.DynamicTextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScoreText extends DynamicTextEntity {
    private int playerScore = 0;

    public ScoreText(Coordinate2D location) {
        super(location, "Score: 0");

        setFill(Color.CYAN);
        setFont(Font.font("Monospaced", FontWeight.BOLD, 25));
    }

    // Zet de score
    public void setScore(int score) {
        playerScore += score;
        setText("Score: " + playerScore);
    }
}
