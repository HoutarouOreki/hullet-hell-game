package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.input.Controls;
import org.mini2Dx.core.graphics.Graphics;

public class Switch extends MenuComponent {
    public SwitchListener listener;
    private boolean value;

    public Switch(SwitchListener listener) {
        this.listener = listener;
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
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(UNACTIVE_COLOR);
        float halfWidth = getSize().x * 0.5f;
        g.fillRect(getPosition().x + (getValue() ? 0 : halfWidth),
                getPosition().y, halfWidth, getSize().y);
        g.setColor(ACTIVE_COLOR);
        g.fillRect(getPosition().x + (!getValue() ? 0 : halfWidth),
                getPosition().y, halfWidth, getSize().y);
        g.setColor(Color.WHITE);
        g.drawString(getValue() ? "ON" : "OFF",
                getPosition().x + (!getValue() ? 0 : halfWidth),
                getPosition().y + (getSize().y - g.getFont().getCapHeight()) * 0.5f,
                halfWidth, Align.center);
    }

    public interface SwitchListener {
        void onValueChanged(boolean newValue);
    }
}
