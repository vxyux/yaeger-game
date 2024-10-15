package org.spacex.ui;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.UpdateExposer;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import javafx.scene.paint.Color;

public class HealthBar extends DynamicRectangleEntity implements SceneBorderTouchingWatcher, UpdateExposer {
    private int currentHealth;
    private final int maxHealth;
    double position;

    public HealthBar(Coordinate2D initialLocation, int maxHealth) {
        super(initialLocation);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.position = initialLocation.getX();

        // zorgt ervoor dat de Healthbar net onder de PlayerShip komt te staan
        setAnchorLocationX(initialLocation.getX() + 25);
        setAnchorLocationY(initialLocation.getY() + 75);

        // defineert de style van de HealthBar
        setWidth(55);
        setHeight(10);
        setFill(Color.GREEN);
        setStrokeWidth(2);
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
        setWidth(55 * healthPercentage);
        // verander de HealthBar van kleur afhankelijk van de percentage double
        if (healthPercentage > 0.5) {
            setFill(Color.GREEN);
        } else if (healthPercentage > 0.2) {
            setFill(Color.YELLOW);
        } else {
            setFill(Color.RED);
        }
    }

    // zorgt ervoor dat de HealthBar de PlayerShip volgt
    public void move(double speed, double direction) {
        setMotion(speed, direction);
    }

    // zorgt ervoor dat de HealthBar in beeld blijft en niet weg glijdt
    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        double currentX = getAnchorLocation().getX();
        double currentY = getAnchorLocation().getY();

        switch (sceneBorder) {
            case BOTTOM:
                if (currentY + getHeight() / 2 > getSceneHeight()) {
                    setAnchorLocationY(getSceneHeight() - getHeight() / 2);
                }
                break;
            case LEFT:
                if (currentX - getWidth() / 2 < 0) {
                    setAnchorLocationX(getWidth() / 2);
                }
                break;
            case RIGHT:
                if (currentX + getHeight() / 2 > getSceneWidth()) {
                    setAnchorLocationX(getSceneWidth() - getHeight() / 2);
                }
                break;
            default:
                break;
        }
    }

    /*
        Handmatig checken of de bar op de juiste plek zal staan
        als het de top van het scherm bereikt.
     */
    @Override
    public void explicitUpdate(long l) {
        // Calculate the scene height
        double barHeight = getHeight();

        // Define a detection offset to keep the bar away from the top
        double detectionOffset = 67;  // You can change this to any offset you want

        // Get the current Y position of the health bar
        double currentY = getAnchorLocation().getY();

        // Manually check the Y position to prevent the bar from moving above the offset
        if (currentY - barHeight / 2 < detectionOffset) {
            // If the health bar is too close to the top, apply the offset to keep it down
            setAnchorLocationY(detectionOffset + barHeight / 2);
        }
    }
}
