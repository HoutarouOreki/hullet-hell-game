package com.houtarouoreki.hullethell.ui;


import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.bindables.Bindable;
import com.houtarouoreki.hullethell.graphics.Drawable;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.numbers.LoopInt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Menu extends Drawable implements ControlProcessor {
    public final Bindable<MenuComponent> currentlyFocused = new Bindable<>(null);
    public final List<MenuComponent> components = new ArrayList<>();

    @Override
    public boolean handleControl(Controls control) {
        MenuComponent focusedComponent = currentlyFocused.getValue();
        if (focusedComponent == null)
            return false;
        if (focusedComponent.handleControl(control))
            return true;

        MenuComponent newFocusNeighbor = getNeighbor(focusedComponent, control);
        if (newFocusNeighbor != null) {
            focusedComponent.unfocus();
            newFocusNeighbor.focus();
            playSound();
            return true;
        }
        return false;
    }

    public void add(MenuComponent child) {
        child.addListener(new MenuComponent.MenuComponentListener() {
            @Override
            public void onFocused() {
                currentlyFocused.setValue(child);
            }

            @Override
            public void onFocusLost() {
            }
        });
        super.add(child);
        components.add(child);
        if (child.isCurrentlyFocused())
            currentlyFocused.setValue(child);
    }

    @Override
    public void addAll(Collection<? extends Drawable> collection) {
        for (Drawable drawable : collection) {
            if (drawable instanceof MenuComponent)
                add((MenuComponent) drawable);
            else
                add(drawable);
        }
    }

    public void interconnectComponentsVertically(boolean focusFirst) {
        LoopInt i = new LoopInt(components);
        do {
            MenuComponent component = components.get(i.getValue());
            component.upperNeighbor = components.get(i.decremented());
            component.lowerNeighbor = components.get(i.incremented());
            if (focusFirst && i.getValue() == 0)
                component.focus();
            i.increment();
        } while (i.getValue() != 0);
    }

    private void playSound() {
        HulletHellGame.getSoundManager().playSound("button1", 0.5f);
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
