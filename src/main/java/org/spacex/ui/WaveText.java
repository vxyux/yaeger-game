package org.spacex.ui;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.DynamicTextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WaveText extends DynamicTextEntity {
    private int currentWave = 1;

    public WaveText(Coordinate2D location) {
        super(location, "Wave 1");

        setFill(Color.RED);
        setFont(Font.font("Monospaced", FontWeight.BOLD, 15));
    }

    public void setWave(int wave) {
        currentWave = wave;
        setText("Wave " + currentWave);
    }
}
