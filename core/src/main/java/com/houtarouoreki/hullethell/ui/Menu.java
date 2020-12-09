package com.houtarouoreki.hullethell.ui;


import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Menu implements ControlProcessor {
    public final List<MenuComponent> components;

    public Menu() {
        components = new ArrayList<MenuComponent>();
    }

    public void update() {
        for (MenuComponent component : components) {
            component.update();
        }
    }

    public void render(Graphics g) {
        for (MenuComponent component : components) {
            component.render(g);
        }
    }

    public MenuComponent getCurrentlyFocusedComponent() {
        for (MenuComponent component : components) {
            if (component.isCurrentlyFocused())
                return component;
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
