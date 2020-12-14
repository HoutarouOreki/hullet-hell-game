package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.bindables.Bindable;
import com.houtarouoreki.hullethell.bindables.ValueChangeListener;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Rectangle;
import com.houtarouoreki.hullethell.input.Controls;

import java.util.EnumSet;

public class Switch extends MenuComponent {
    public final Bindable<Boolean> value;
    private final Rectangle onRect;
    private final Label label;

    public Switch() {
        value = new Bindable<Boolean>(false);
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
        label.alignment = Align.center;
        add(label);

        value.addListener(new ValueChangeListener<Boolean>() {
            @Override
            public void onValueChanged(Boolean oldValue, Boolean newValue) {
                generateLayout();
            }
        }, true);
    }

    @Override
    public boolean handleControl(Controls control) {
        switch (control) {
            case select:
                setValue(!value.getValue());
                playSound();
                return true;
            case left:
                if (!getValue())
                    return false;
                setValue(false);
                playSound();
                return true;
            case right:
                if (getValue())
                    return false;
                setValue(true);
                playSound();
                return true;
        }
        return false;
    }

    private void playSound() {
        HulletHellGame.getSoundManager().playSound("button1", 0.5f);
    }

    public boolean getValue() {
        return value.getValue();
    }

    public void setValue(boolean newValue) {
        value.setValue(newValue);
    }

    private void generateLayout() {
        Vector2 onPosition = new Vector2(value.getValue() ? 0.5f : 0, 0);
        onRect.setPosition(onPosition);
        label.setPosition(onPosition);
        label.setText(value.getValue() ? "ON" : "OFF");
    }
}
