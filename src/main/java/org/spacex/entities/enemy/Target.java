package org.spacex.entities.enemy;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import org.spacex.entities.bullet.HeroBullet;

import java.util.List;
import java.util.Random;

public abstract class Target extends DynamicSpriteEntity implements SceneBorderTouchingWatcher, Collided {

    private int health;

    public Target(String sprite, Coordinate2D location, Size size, int hp) {
        super(sprite, location, size);

        this.health = hp;
    }

    // Checkt of het een rand van de speelscherm raakt/
    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {}

    // Checkt of er een collisie is met een HeroBullet
    public void onCollision(List<Collider> list) {
        // checks all the collisions that are made
        for (Collider collider : list) {
            if (collider instanceof HeroBullet) {
                // Remove the bullet
                ((HeroBullet) collider).remove();
                calculateHealth();
            }
        }
    }

    // Calculleert de HP percentage en checkt of het 0 is
    public void calculateHealth() {
        health--;
        if(health == 0) {
            // remove self
            remove();
            // kaboom!
            explode();
        }
    }

    /*
    Zorgt voor een random spawncooldown,
    zodat er niet gelijk met andere entiteiten wordt geschoten
    */
    public int getRandomSpawnCooldown() {
        // nieuwe random getal voor de spawn cooldown zodat het niet gelijk schiet
        int max = 4000, min = 1000;
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    // Zorgt voor een explosie
    public abstract void explode();
}

