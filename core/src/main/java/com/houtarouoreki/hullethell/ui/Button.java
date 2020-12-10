package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.input.Controls;
import org.mini2Dx.core.graphics.Graphics;

public class Button extends MenuComponent {
    public String label = "";
    public ButtonListener listener;

    @Override
    public boolean handleControl(Controls control) {
        if (control == Controls.select && listener != null) {
            listener.onAction();
            return true;
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(isCurrentlyFocused() ? ACTIVE_COLOR : UNACTIVE_COLOR);
        g.fillRect(getPosition().x, getPosition().y, getSize().x, getSize().y);
        g.setColor(Color.WHITE);
        g.drawString(label, getPosition().x, getPosition().y +
                (getSize().y - g.getFont().getCapHeight()) * 0.5f, getSize().x, Align.center);
    }

    public interface ButtonListener {
        void onAction();
    }
}
