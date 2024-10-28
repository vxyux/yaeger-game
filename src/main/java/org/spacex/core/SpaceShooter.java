package org.spacex.core;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import org.spacex.core.additional.GameOverScreen;
import org.spacex.core.additional.TitleScene;

/**
 * Hello world!
 *
 */
public class SpaceShooter extends YaegerGame
{
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void setupGame() {
        setGameTitle("Astro Blaster");
        setSize(new Size(1200, 800));
    }

    @Override
    public void setupScenes() {
        addScene(0, new TitleScene(this));
        addScene(1, new GameScene(this));
        addScene(2, new GameOverScreen(this));
    }

    public void restartGame() {
        GameScene gameScene = new GameScene(this);
        addScene(1, new GameScene(this));
        setActiveScene(1);
    }
}
