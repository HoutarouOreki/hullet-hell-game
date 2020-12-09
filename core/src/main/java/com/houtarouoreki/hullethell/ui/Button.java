package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.input.Controls;
import org.mini2Dx.core.graphics.Graphics;

public class Button extends MenuComponent {
    public String label = "";
    public Vector2 position;
    public Vector2 size;
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
        g.setColor(isCurrentlyFocused() ? Color.valueOf("0066cc") : Color.valueOf("204060"));
        g.fillRect(position.x, position.y, size.x, size.y);
        g.setColor(Color.WHITE);
        g.drawString(label, position.x, position.y +
                (size.y - g.getFont().getCapHeight()) * 0.5f, size.x, Align.center);
    }

    public interface ButtonListener {
        void onAction();
    }
}
