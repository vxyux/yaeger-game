package org.spacex.ui.element;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;

public abstract class UiElement extends TextEntity {
    public UiElement(Coordinate2D initialLocation) {
        super(initialLocation);
    }

    public void setUiElement(int number) {

    };
}
