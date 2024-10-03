package org.spacex;

import com.github.hanyaeger.api.scenes.DynamicScene;

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

    }
}
