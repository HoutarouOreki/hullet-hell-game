package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import org.mini2Dx.core.graphics.Graphics;

public abstract class MenuComponent implements ControlProcessor {
    public static Color ACTIVE_COLOR = Color.valueOf("0066cc");
    public static Color UNACTIVE_COLOR = Color.valueOf("204060");
    public MenuComponent lowerNeighbor;
    public MenuComponent upperNeighbor;
    public MenuComponent leftNeighbor;
    public MenuComponent rightNeighbor;
    private Vector2 position = new Vector2();
    private Vector2 size = new Vector2();
    private boolean currentlyFocused;

    public void update(float delta) {
    }

    public void render(Graphics g) {
    }

    public final boolean isCurrentlyFocused() {
        return currentlyFocused;
    }

    public final void focus() {
        currentlyFocused = true;
        onFocused();
    }

    public final void unfocus() {
        currentlyFocused = false;
        onFocusLost();
    }

    protected void onFocused() {
    }

    protected void onFocusLost() {
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(Vector2 position) {
        this.position = position.cpy();
    }

    public Vector2 getSize() {
        return size.cpy();
    }

    public void setSize(Vector2 size) {
        this.size = size.cpy();
    }
}
