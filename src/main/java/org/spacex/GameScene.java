package org.spacex;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.scenes.DynamicScene;
import org.spacex.components.BackAndForthMovement;
import org.spacex.components.ExplosionCreator;
import org.spacex.components.MovementPattern;
import org.spacex.entities.*;

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
        //  Setup Movement Pattern for Boss Ship
        MovementPattern backAndForthMovement = new BackAndForthMovement(2, getWidth());

        // hier wordt de PlayerShip gespawnt
        PlayerShip player = new PlayerShip(new Coordinate2D(getWidth() / 2, getHeight() / 2), spaceShooter, this);
        addEntity(player);

        EnemyShip enemy = new EnemyShip(new Coordinate2D(getWidth() / 2, getHeight() / 4), this);
        addEntity(enemy);

        BossShip boss = new BossShip(new Coordinate2D(getWidth() / 2 - 75, 50), "sprites/dragonboss_ship.png" ,  backAndForthMovement, this);
        addEntity(boss);
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


    // dit is een test functie
    public void update() {
        System.out.println("Scene is updating");

        // Andere logica hier
    }


}
