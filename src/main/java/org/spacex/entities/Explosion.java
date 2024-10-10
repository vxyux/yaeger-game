package org.spacex.entities;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.media.SoundClip;

public class Explosion extends DynamicSpriteEntity {
    private int frameIndex = 0;

    public Explosion(final Coordinate2D initialLocation) {
        this(initialLocation, 0, 0);
    }

    public Explosion(final Coordinate2D initialLocation, final double speed, final double direction) {
        super("sprites/explosion.png", initialLocation, new Size(90, 90), 1, 17);
        setAnchorPoint(AnchorPoint.CENTER_CENTER);
        setMotion(speed, direction);
        new SoundClip("audios/laser.mp3").play();
    }

    public void updateFrame() {
        if (getCurrentFrameIndex() < getFrames() - 1) {
            frameIndex++;
            setCurrentFrameIndex(frameIndex);
        } else {
            remove();
        }
    }
}
