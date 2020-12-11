package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Rectangle;
import com.houtarouoreki.hullethell.input.Controls;

import java.util.EnumSet;

public class Switch extends MenuComponent {
    private final Rectangle onRect;
    private final Label label;
    public SwitchListener listener;
    private boolean value;

    public Switch(SwitchListener listener) {
        this.listener = listener;

        EnumSet<Axes> bothAxes = EnumSet.of(Axes.HORIZONTAL, Axes.VERTICAL);
        Rectangle offRect = new Rectangle();
        offRect.setRelativePositionAxes(bothAxes);
        offRect.setRelativeSizeAxes(bothAxes);
        offRect.setSize(new Vector2(1, 1));
        offRect.setColor(UNACTIVE_COLOR);
        add(offRect);

        onRect = new Rectangle();
        onRect.setRelativePositionAxes(bothAxes);
        onRect.setRelativeSizeAxes(bothAxes);
        onRect.setSize(new Vector2(0.5f, 1));
        onRect.setColor(ACTIVE_COLOR);
        add(onRect);

        label = new Label();
        label.setRelativePositionAxes(bothAxes);
        label.setRelativeSizeAxes(bothAxes);
        label.setSize(new Vector2(0.5f, 1));
        add(label);
    }

    @Override
    public boolean handleControl(Controls control) {
        switch (control) {
            case select:
                setValue(!value);
                return true;
            case left:
                setValue(false);
                return true;
            case right:
                setValue(true);
                return true;
        }
        return false;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean newValue) {
        value = newValue;
        listener.onValueChanged(newValue);
        Vector2 onPosition = new Vector2(newValue ? 0.5f : 0, 0);
        onRect.setPosition(onPosition);
        label.setPosition(onPosition);
        label.setText(newValue ? "ON" : "OFF");
    }

    public interface SwitchListener {
        void onValueChanged(boolean newValue);
    }
}
