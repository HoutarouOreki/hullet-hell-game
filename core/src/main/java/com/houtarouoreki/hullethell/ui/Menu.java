package com.houtarouoreki.hullethell.ui;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Menu implements InputProcessor {
    public final List<MenuComponent> components;

    public Menu() {
        components = new ArrayList<MenuComponent>();
    }

    public void update(float delta) {
        for (MenuComponent component : components) {
            component.update(delta);
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
    public boolean keyDown(int keycode) {
        MenuComponent focusedComponent = getCurrentlyFocusedComponent();
        if (components.size() == 0)
            return false;
        if (keycode == Input.Keys.TAB) {
            handleTabKey(focusedComponent);
            return true;
        }
        return focusedComponent.handleKey(keycode);
    }

    private void handleTabKey(MenuComponent focusedComponent) {
        if (components.size() <= 1)
            return;
        if (focusedComponent == null) {
            components.get(0).setCurrentlyFocused(true);
            return;
        }
        int indexFocused = components.indexOf(focusedComponent);
        if (indexFocused == components.size() - 1)
            components.get(0).setCurrentlyFocused(true);
        focusedComponent.setCurrentlyFocused(false);
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
