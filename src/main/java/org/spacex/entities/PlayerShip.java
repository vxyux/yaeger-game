package org.spacex.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Newtonian;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import org.spacex.SpaceShooter;

import java.util.Set;

public class PlayerShip extends DynamicSpriteEntity implements Newtonian, KeyListener, SceneBorderTouchingWatcher {
    private SpaceShooter spaceShooter;

    protected PlayerShip(String resource, Coordinate2D initialLocation) {
        super(resource, initialLocation);
    }

    public PlayerShip(Coordinate2D location, SpaceShooter spaceShooter) {
        super("sprites/playership.png", location, new Size(80, 80));

        this.spaceShooter = spaceShooter;
        setGravityConstant(0);
        // dit trekt de player naar beneden
        // setFrictionConstant(0.1);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder sceneBorder) {
        setSpeed(0);

        switch(sceneBorder){
            case TOP:
                setAnchorLocationY(1);
                break;
            case BOTTOM:
                setAnchorLocationY(getSceneHeight() - getHeight() - 1);
                break;
            case LEFT:
                setAnchorLocationX(1);
                break;
            case RIGHT:
                setAnchorLocationX(getSceneWidth() - getWidth() - 1);
            default:
                break;
        }
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if(pressedKeys.contains(KeyCode.LEFT)){
            setCurrentFrameIndex(0);
            setMotion(3,270d);
        } else if(pressedKeys.contains(KeyCode.RIGHT)){
            setCurrentFrameIndex(1);
            setMotion(3,90d);
        } else if(pressedKeys.contains(KeyCode.UP)){
            setMotion(3,180d);
        } else if(pressedKeys.contains(KeyCode.DOWN)) {
            setMotion(3, 0d);
        }
        else if(pressedKeys.isEmpty()){
            setSpeed(0);
        }
    }
}
