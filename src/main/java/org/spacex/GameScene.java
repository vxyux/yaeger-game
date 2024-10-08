package org.spacex;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.EntitySpawnerContainer;
import com.github.hanyaeger.api.scenes.DynamicScene;
import org.spacex.entities.Bullet;
import org.spacex.entities.BulletSpawner;
import org.spacex.entities.PlayerShip;

public class GameScene extends DynamicScene implements EntitySpawnerContainer {
    private SpaceShooter spaceShooter;
    private BulletSpawner bulletSpawner;

    public GameScene(SpaceShooter spaceShooter) {
        this.spaceShooter = spaceShooter;
    }

    @Override
    public void setupScene() {
        setBackgroundImage("backgrounds/space.png");
        bulletSpawner = new BulletSpawner(new Coordinate2D(getWidth() / 2, getHeight()));
    }

    public GameScene getGameScene(){
        return this;
    }


    @Override
    public void setupEntities() {
        PlayerShip player = new PlayerShip(new Coordinate2D(30,30), spaceShooter, bulletSpawner);
        addEntity(player);

//        Bullet playerBeam = new Bullet("sprites/laser_beam.png", new Coordinate2D(600, 800));
//        playerBeam.setAnchorPoint(AnchorPoint.CENTER_CENTER);
//        addEntity(playerBeam);
    }

    @Override
    public void setupEntitySpawners() {
            addEntitySpawner(bulletSpawner);
    }
}
