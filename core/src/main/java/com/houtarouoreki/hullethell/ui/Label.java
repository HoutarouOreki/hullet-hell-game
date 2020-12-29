package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.graphics.Fonts;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.font.FontGlyphLayout;
import org.mini2Dx.core.font.GameFont;
import org.mini2Dx.core.graphics.Graphics;

public class Label extends MenuComponent {
    public int alignment = Align.topLeft;
    public GameFont font;
    private String text = "";
    public Vector2 lastComputedSize;

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
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
        FontGlyphLayout glyphLayout = font.getSharedGlyphLayout();
        glyphLayout.setText(getText());
        lastComputedSize = new Vector2(glyphLayout.getWidth(), glyphLayout.getHeight());
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
