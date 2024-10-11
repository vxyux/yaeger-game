package org.spacex;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.scenes.DynamicScene;
import org.spacex.components.ExplosionCreator;
import org.spacex.entities.Bullet;
import org.spacex.entities.EnemyShip;
import org.spacex.entities.Explosion;
import org.spacex.entities.PlayerShip;

/*
    GameScene implementeert van ExplosionCreator, een interface. Dat verplicht
    GameScene om een methode te hebben genaamd createExplosion.

    CreateExplosion zorgt ervoor dat de explosie wordt aangemaakt/toegevoegt aan
    de GameScene.
*/
public class GameScene extends DynamicScene implements ExplosionCreator {
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

        EnemyShip enemy = new EnemyShip(new Coordinate2D(getWidth() / 2, getHeight() / 4), this);
        addEntity(enemy);
    }

    // zo kan addEntity aangeroepen worden (met Bullet als parameter)!
    public void addBullet(Bullet newBullet) {
        newBullet.setAnchorPoint(AnchorPoint.CENTER_LEFT);
        addEntity(newBullet);
    }

/*
    Voegt een nieuwe explosie sprite toe, afhankelijk van de locatie
    Wordt alleen aangeroepen in andere klassen
*/
    public void createExplosion(Coordinate2D anchorLocation, double speed, Size explosionSize) {
        addEntity(new Explosion(anchorLocation, speed, explosionSize));
    }
}
