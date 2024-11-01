package org.spacex.ui.element;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import javafx.scene.paint.Color;

public class HealthBar extends DynamicRectangleEntity {
    private int currentHealth;
    private final int maxHealth;

    public HealthBar(Coordinate2D initialLocation, int maxHealth) {
        super(initialLocation);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;

        // defineert style van de HealthBar
        setWidth(getSceneWidth());
        setHeight(10);
        setFill(Color.GREEN);
        setStrokeWidth(3);
        setAnchorLocationY(790);
    }

    /*
        Zorgt ervoor dat de health aanpasbaar wordt voor andere
        entiteiten
     */
    public void setCurrentHealth(int currentHealth) {
        // voorkomt negatief levenspunten
        if (currentHealth < 0) {
            this.currentHealth = 0;
        // voorkomt meer levens voor de speler
        } else if (currentHealth > maxHealth) {
            this.currentHealth = maxHealth;
        } else {
            this.currentHealth = currentHealth;
        }
        updateHealthBar();
    }

    private void updateHealthBar() {
        // berekent de percentage om de HealthBar correct te tonen
        double healthPercentage = (double) currentHealth / maxHealth;
        setWidth(getSceneWidth() * healthPercentage);
        // verander de HealthBar van kleur afhankelijk van de percentage double
        if (healthPercentage > 0.5) {
            setFill(Color.GREEN);
        } else if (healthPercentage > 0.2) {
            setFill(Color.YELLOW);
        } else {
            setFill(Color.RED);
        }
    }
}
