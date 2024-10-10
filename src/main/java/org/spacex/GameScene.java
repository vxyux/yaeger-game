package org.spacex;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.media.SoundClip;
import com.github.hanyaeger.api.scenes.DynamicScene;
import org.spacex.entities.Bullet;
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
        // hier wordt de PlayerShip gespawnt
        PlayerShip player = new PlayerShip(new Coordinate2D(getWidth() / 2, getHeight() / 2), spaceShooter, this);
        addEntity(player);
    }

    // zo kan addEntity aangeroepen worden (met Bullet als parameter)!
    public void addBullet(Bullet newBullet) {
        newBullet.setAnchorPoint(AnchorPoint.CENTER_LEFT);
        addEntity(newBullet);
    }
}
