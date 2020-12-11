package com.houtarouoreki.hullethell.ui;


import com.houtarouoreki.hullethell.graphics.Drawable;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;

public class Menu extends Drawable implements ControlProcessor {
    public MenuComponent getCurrentlyFocusedComponent() {
        for (Drawable component : children) {
            if (component instanceof MenuComponent
                    && ((MenuComponent) component).isCurrentlyFocused())
                return (MenuComponent) component;
        }
        return null;
    }

    @Override
    public boolean handleControl(Controls control) {
        MenuComponent focusedComponent = getCurrentlyFocusedComponent();
        if (focusedComponent == null)
            return false;
        if (focusedComponent.handleControl(control))
            return true;

        MenuComponent newFocusNeighbor = getNeighbor(focusedComponent, control);
        if (newFocusNeighbor != null) {
            focusedComponent.unfocus();
            newFocusNeighbor.focus();
            return true;
        }
        return false;
    }

    private MenuComponent getNeighbor(MenuComponent focusedComponent, Controls control) {
        switch (control) {
            case up:
                return focusedComponent.upperNeighbor;
            case down:
                return focusedComponent.lowerNeighbor;
            case left:
                return focusedComponent.leftNeighbor;
            case right:
                return focusedComponent.rightNeighbor;
        }
        return null;
    }
}
