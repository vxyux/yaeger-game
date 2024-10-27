package org.spacex.components.movementpattern;

import com.github.hanyaeger.api.Coordinate2D;

import java.util.Random;

public class RandomizedMovement implements MovementPattern {
    private double speedX;       // Snelheid in X-richting
    private double speedY;       // Snelheid in Y-richting
    private double sceneWidth;   // Breedte
    private double sceneHeight;  // Hoogte
    private Random random = new Random(); // Random integer
    private long changeDirectionTime; // Tijd wanneer de richting voor het laatst is veranderd
    private final long changeInterval; // Interval voor het veranderen van richting
    private Coordinate2D direction; // Huidige bewegingsrichting

    public RandomizedMovement(double speedX, double speedY, double sceneWidth, double sceneHeight, long changeInterval) {
        this.speedX = speedX;
        this.speedY = speedY;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.changeInterval = changeInterval; // Tijd in milliseconden voor verandering
        this.changeDirectionTime = System.currentTimeMillis(); // Starttijd
        this.direction = new Coordinate2D(speedX, speedY); // Beginrichting
    }

    @Override
    public Coordinate2D calculateNextPosition(Coordinate2D currentPosition) {
        long currentTime = System.currentTimeMillis();

        // Verander richting als het interval is verstreken
        if (currentTime - changeDirectionTime >= changeInterval) {
            direction = new Coordinate2D(random.nextDouble() * speedX * 2 - speedX,
                    random.nextDouble() * speedY * 2 - speedY); // Willekeurige richting
            changeDirectionTime = currentTime; // Reset de tijd
        }

        double newX = currentPosition.getX() + direction.getX();
        double newY = currentPosition.getY() + direction.getY();

        // Controleer of de nieuwe X binnen de grenzen van de scène ligt
        if (newX < 0 || newX > sceneWidth - 150) { // 150 is de breedte van de Boss
            direction = new Coordinate2D(-direction.getX(), direction.getY()); // Omgekeerde richting in X
            newX = Math.max(0, Math.min(newX, sceneWidth - 150)); // Blijf binnen de grenzen
        }

        // Controleer of de nieuwe Y binnen de grenzen van de scène ligt
        if (newY < 0 || newY > sceneHeight - 150) { // 150 is de hoogte van de Boss
            direction = new Coordinate2D(direction.getX(), -direction.getY()); // Omgekeerde richting in Y
            newY = Math.max(0, Math.min(newY, sceneHeight - 150)); // Blijf binnen de grenzen
        }

        return new Coordinate2D(newX, newY);
    }
}
