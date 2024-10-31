package org.spacex.targetmovement;

import com.github.hanyaeger.api.Coordinate2D;


public class BackAndForthMovement implements MovementPattern {
    private final double speed;
    private boolean movingRight = true;
    private final double sceneWidth;

    public BackAndForthMovement(double speed, double sceneWidth) {
        this.speed = speed;
        this.sceneWidth = sceneWidth;
    }

    /*
    Override van super (mocht het nodig zijn).

    Calculeert de volgende positie op basis van de movingRight.
    Als movingright true is == beweegt Boss naar rechts.
    if (position >= sceneWidth) == raakt de 'rand' aan,
    en gaat bewegen naar links als gevolg.
    vice versa.
     */
    @Override
    public Coordinate2D calculateNextPosition(Coordinate2D currentPosition) {
        double position = currentPosition.getX();

        // Als Boss beweegt naar rechts
        if (movingRight) {
            position += speed;
            // Als Boss in de buurt komt van de rechter schermrand
            if (position >= sceneWidth) {
                movingRight = false;
            }

        } else {
            position -= speed;
            // Als Boss in de buurt komt van de linker schermrand
            if (position <= 0) {
                movingRight = true;
            }
        }
        return new Coordinate2D(position, currentPosition.getY());
    }


}

