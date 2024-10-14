package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import org.spacex.GameScene;
import org.spacex.SpaceShooter;

import java.util.Set;

public class Target extends DynamicSpriteEntity implements KeyListener, SceneBorderTouchingWatcher {

    private int health;

    public Target(String sprite, Coordinate2D location, Size size) {
        super(sprite, location, size);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {

    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> set) {

    }

    public void explode() {

    }

    public void onUpdate(){

    }
}

