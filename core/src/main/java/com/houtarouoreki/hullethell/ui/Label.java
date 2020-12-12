package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.graphics.Fonts;
import com.houtarouoreki.hullethell.input.Controls;
import org.mini2Dx.core.font.GameFont;
import org.mini2Dx.core.graphics.Graphics;

public class Label extends MenuComponent {
    public int alignment = Align.topLeft;
    public GameFont font;
    private String text = "";

    public Label() {
        font = Fonts.defaultFont;
    }

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
        if (font == null)
            return;
        g.setFont(font);
        g.drawString(text, getRenderPosition().x,
                getRenderPosition().y + (alignment == Align.center ?
                        (getRenderSize().y - g.getFont().getCapHeight()) * 0.5f : 0),
                getRenderSize().x, alignment);
    }
}
