package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import org.spacex.GameScene;
import org.spacex.SpaceShooter;
import org.spacex.entities.bullet.HeroBullet;

import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Target extends DynamicSpriteEntity implements SceneBorderTouchingWatcher, Collided {

    private int health;

    public Target(String sprite, Coordinate2D location, Size size, int hp) {
        super(sprite, location, size);

        this.health = hp;
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {}

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

    public void calculateHealth() {
        health--;
        if(health == 0) {
            // remove self
            remove();
            // kaboom!
            explode();
        }
    }

    public int getRandomSpawnCooldown() {
        // nieuwe random getal voor de spawn cooldown zodat het niet gelijk schiet
        int max = 4000, min = 1000;
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public abstract void explode();
}

