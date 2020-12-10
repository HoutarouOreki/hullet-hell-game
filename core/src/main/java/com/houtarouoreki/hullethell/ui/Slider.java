package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.input.InputManager;
import com.houtarouoreki.hullethell.numbers.LimitedNumber;
import org.mini2Dx.core.graphics.Graphics;

public class Slider extends MenuComponent {
    private final InputManager inputManager;
    private float leftTimeOut = 0;
    private float rightTimeOut = 0;
    public LimitedNumber<Integer> value;

    public Slider(int startValue, int min, int max, InputManager inputManager) {
        this.inputManager = inputManager;
        value = new LimitedNumber<Integer>(startValue, min, max);
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
    public void render(Graphics g) {
        super.render(g);
        g.setColor(UNACTIVE_COLOR);
        g.fillRect(getPosition().x, getPosition().y, getSize().x, getSize().y);
        g.setColor(ACTIVE_COLOR);
        g.fillRect(getPosition().x, getPosition().y, (value.getValue() / (float)value.getMax())
                * getSize().x, getSize().y);
        g.setColor(Color.WHITE);
        g.drawString(value.getValue().toString(), getPosition().x, getPosition().y
                + (getSize().y - g.getFont().getCapHeight()) * 0.5f, getSize().x, Align.center);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (isCurrentlyFocused())
            applyHeldKeys(delta);
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
