package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.input.Controls;
import org.mini2Dx.core.graphics.Graphics;

public class Label extends MenuComponent {
    public int alignment = Align.left;
    private String text = "";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean handleControl(Controls control) {
        return false;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.WHITE);
        g.drawString(text, getPosition().x,
                getPosition().y + (getSize().y - g.getFont().getCapHeight()) * 0.5f,
                getSize().x, alignment);
    }
}
