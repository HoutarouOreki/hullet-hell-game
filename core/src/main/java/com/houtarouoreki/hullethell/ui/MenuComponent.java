package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.graphics.Color;
import com.houtarouoreki.hullethell.graphics.Drawable;
import com.houtarouoreki.hullethell.input.ControlProcessor;

public abstract class MenuComponent extends Drawable implements ControlProcessor {
    public static Color ACTIVE_COLOR = Color.valueOf("0066cc");
    public static Color UNACTIVE_COLOR = Color.valueOf("204060");
    public MenuComponent lowerNeighbor;
    public MenuComponent upperNeighbor;
    public MenuComponent leftNeighbor;
    public MenuComponent rightNeighbor;
    private boolean currentlyFocused;

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
}
