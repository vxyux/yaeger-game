package org.spacex.utils;

import com.github.hanyaeger.api.Coordinate2D;
import java.util.List;
import java.util.Random;

public class PositionManager {

    public static void generatePossiblePositions(List<Coordinate2D> availablePositions, List<Coordinate2D> usedPositions, double width, double minDistance) {
        double minX = 50;
        double maxX = width - 100;
        double minY = 50;
        double maxY = 200;

        for (double x = minX; x <= maxX; x += minDistance) {
            for (double y = minY; y <= maxY; y += minDistance) {
                availablePositions.add(new Coordinate2D(x, y));
            }
        }
    }

    public static Coordinate2D getValidPosition(List<Coordinate2D> availablePositions, List<Coordinate2D> usedPositions, Random random) {
        if (availablePositions.isEmpty()) {
            resetPositions(availablePositions, usedPositions);
        }
        int randomIndex = random.nextInt(availablePositions.size());
        Coordinate2D chosenPosition = availablePositions.remove(randomIndex);
        usedPositions.add(chosenPosition);
        return chosenPosition;
    }

    private static void resetPositions(List<Coordinate2D> availablePositions, List<Coordinate2D> usedPositions) {
        availablePositions.addAll(usedPositions);
        usedPositions.clear();
    }
}
