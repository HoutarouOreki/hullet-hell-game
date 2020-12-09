package com.houtarouoreki.hullethell.ui;

import org.mini2Dx.core.graphics.Graphics;

public abstract class MenuComponent {
    private boolean currentlyFocused;

    public void update(float delta) {
    }

    public void render(Graphics g) {
    }

    public boolean isCurrentlyFocused() {
        return currentlyFocused;
    }

    public void setCurrentlyFocused(boolean value) {
        currentlyFocused = value;
    }

    public abstract boolean handleKey(int key);
}
