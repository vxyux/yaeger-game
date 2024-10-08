package org.spacex;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.scenes.DynamicScene;
import org.spacex.entities.PlayerShip;

public class GameScene extends DynamicScene {
    private SpaceShooter spaceShooter;

    public GameScene(SpaceShooter spaceShooter) {
        this.spaceShooter = spaceShooter;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/space.png");
    }

    @Override
    public void setupEntities() {
        PlayerShip player = new PlayerShip(new Coordinate2D(30,30), spaceShooter);
        addEntity(player);

//        Bullet playerBeam = new Bullet("sprites/laser_beam.png", new Coordinate2D(600, 800));
//        playerBeam.setAnchorPoint(AnchorPoint.CENTER_CENTER);
//        addEntity(playerBeam);
    }
}
