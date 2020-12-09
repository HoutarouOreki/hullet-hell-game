package com.houtarouoreki.hullethell.ui;

import com.houtarouoreki.hullethell.input.ControlProcessor;
import org.mini2Dx.core.graphics.Graphics;

public abstract class MenuComponent implements ControlProcessor {
    public MenuComponent lowerNeighbor;
    public MenuComponent upperNeighbor;
    public MenuComponent leftNeighbor;
    public MenuComponent rightNeighbor;
    private boolean currentlyFocused;

    public void update() {
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
}
