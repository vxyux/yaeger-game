package org.spacex.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.media.SoundClip;
import org.spacex.components.ExplosionTimer;

public class Explosion extends DynamicSpriteEntity implements TimerContainer {
    private int frameIndex = 0;

    public Explosion(Coordinate2D initialLocation, double speed, Size size) {
        super("sprites/explosion.png", initialLocation, size, 1, 17);
        setMotion(speed, 90d);
        SoundClip soundClip = new SoundClip("audios/explosion.mp3");
        soundClip.setVolume(0.20);
        soundClip.play();
    }
    public void updateFrame() {
        if (getCurrentFrameIndex() < getFrames() - 1) {
            frameIndex++;
            setCurrentFrameIndex(frameIndex);
        } else {
            remove();
        }
    }
    public void setupTimers() {
        addTimer(new ExplosionTimer(this));
    }
}
