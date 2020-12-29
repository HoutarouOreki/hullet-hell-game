package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.graphics.Axes;
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

    public Label() {
        font = Fonts.defaultFont;
    }

    @Override
    public Vector2 getRenderSize() {
        Vector2 computedSize = getComputedSize();
        if (getRelativeSizeAxes().isEmpty())
            return computedSize;
        Vector2 renderSize = super.getRenderSize();
        if (getRelativeSizeAxes().contains(Axes.HORIZONTAL))
            return new Vector2(renderSize.x, computedSize.y);
        else if (getRelativeSizeAxes().contains(Axes.VERTICAL))
            return new Vector2(computedSize.x, renderSize.y);
        return renderSize;
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
    }

    @Override
    public void draw(Graphics g) {
        if (font == null)
            return;
        g.setFont(font);
        g.drawString(text,
                getRenderPosition().x,
                getRenderPosition().y + (alignment == Align.center ?
                    (getRenderSize().y - g.getFont().getCapHeight()) * 0.5f :
                    0),
                getRenderSize().x,
                alignment);
    }

    private Vector2 getComputedSize() {
        FontGlyphLayout glyphLayout = font.getSharedGlyphLayout();
        glyphLayout.setText(getText());
        return new Vector2(glyphLayout.getWidth(), glyphLayout.getHeight());
    }
}
