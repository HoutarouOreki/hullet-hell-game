package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Rectangle;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.input.InputManager;
import com.houtarouoreki.hullethell.numbers.LimitedNumber;

import java.util.EnumSet;

public class Slider extends MenuComponent {
    private final InputManager inputManager;
    private final Rectangle onRect;
    private final Label label;
    public LimitedNumber<Integer> value;
    private float leftTimeOut = 0;
    private float rightTimeOut = 0;

    public Slider(int startValue, int min, int max, InputManager inputManager) {
        this.inputManager = inputManager;
        value = new LimitedNumber<Integer>(startValue, min, max);

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
        onRect.setColor(ACTIVE_COLOR);
        add(onRect);

        label = new Label();
        label.setRelativePositionAxes(bothAxes);
        label.setRelativeSizeAxes(bothAxes);
        label.setSize(new Vector2(1, 1));
        add(label);
    }

    @Override
    public boolean handleControl(Controls control) {
        float holdKeyTimeOutInitial = 0.25f;
        if (control == Controls.left) {
            decrement();
            leftTimeOut = holdKeyTimeOutInitial;
            return true;
        }
        if (control == Controls.right) {
            increment();
            rightTimeOut = holdKeyTimeOutInitial;
            return true;
        }
        return false;
    }

    private void increment() {
        value.setValue(value.getValue() + 1);
    }

    private void decrement() {
        value.setValue(value.getValue() - 1);
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        if (isCurrentlyFocused())
            applyHeldKeys(delta);
        label.setText(value.getValue().toString());
        float progress = (value.getValue() - value.getMin()) /
                (float)(value.getMax() - value.getMin());
        onRect.setSize(new Vector2(progress, 1f));
    }

    private void applyHeldKeys(float delta) {
        leftTimeOut -= delta;
        rightTimeOut -= delta;
        float holdKeyTimeOutSubsequent = 0.05f;
        if (leftTimeOut <= 0 && inputManager.isControlActive(Controls.left)) {
            decrement();
            leftTimeOut = holdKeyTimeOutSubsequent;
        }
        if (rightTimeOut <= 0 && inputManager.isControlActive(Controls.right)) {
            increment();
            rightTimeOut = holdKeyTimeOutSubsequent;
        }
    }
}