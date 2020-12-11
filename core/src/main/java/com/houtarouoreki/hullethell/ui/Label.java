package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.input.Controls;
import org.mini2Dx.core.graphics.Graphics;

public class Label extends MenuComponent {
    public int alignment = Align.topLeft;
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
    public void draw(Graphics g) {
        g.drawString(text, getRenderPosition().x,
                getRenderPosition().y + (alignment == Align.center ?
                        (getRenderSize().y - g.getFont().getCapHeight()) * 0.5f : 0),
                getRenderSize().x, alignment);
    }
}
