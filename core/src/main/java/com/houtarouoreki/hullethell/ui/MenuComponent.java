package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.graphics.Color;
import com.houtarouoreki.hullethell.graphics.Drawable;
import com.houtarouoreki.hullethell.input.ControlProcessor;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuComponent extends Drawable implements ControlProcessor {
    public static Color ACTIVE_COLOR = Color.valueOf("0066cc");
    public static Color UNACTIVE_COLOR = Color.valueOf("204060");
    private final List<MenuComponentListener> listeners = new ArrayList<MenuComponentListener>();
    public MenuComponent lowerNeighbor;
    public MenuComponent upperNeighbor;
    public MenuComponent leftNeighbor;
    public MenuComponent rightNeighbor;
    private boolean currentlyFocused;

    public <T extends MenuComponent> void addListener(MenuComponentListener listener) {
        listeners.add(listener);
    }

    public <T extends MenuComponent> void removeListener(MenuComponentListener listener) {
        listeners.remove(listener);
    }

    public void clearListeners() {
        listeners.clear();
    }

    public final boolean isCurrentlyFocused() {
        return currentlyFocused;
    }

    public final void focus() {
        currentlyFocused = true;
        onFocused();
        for (MenuComponentListener listener : listeners) {
            listener.onFocused();
        }
    }

    public final void unfocus() {
        currentlyFocused = false;
        onFocusLost();
        for (MenuComponentListener listener : listeners) {
            listener.onFocusLost();
        }
    }

    protected void onFocused() {
    }

    protected void onFocusLost() {
    }

    public interface MenuComponentListener {
        void onFocused();

        void onFocusLost();
    }
}
