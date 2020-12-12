package com.houtarouoreki.hullethell.input;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.HashSet;

public class InputManager implements InputProcessor {
    public final HashSet<ControlProcessor> managedProcessors;
    private final KeyBindingManager keyBindingManager;
    private final HashSet<Integer> pressedKeys;

    public InputManager() {
        managedProcessors = new HashSet<ControlProcessor>();
        keyBindingManager = new KeyBindingManager();
        pressedKeys = new HashSet<Integer>();
    }

    public void update(float delta) {
    }

    public boolean isControlActive(Controls control) {
        for (Integer key : keyBindingManager.getControlKeys(control)) {
            if (pressedKeys.contains(key))
                return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        pressedKeys.add(keycode);
        Controls control = keyBindingManager.getControlFromKey(keycode);
        if (control == null)
            return false;
        for (ControlProcessor processor : new ArrayList<ControlProcessor>(managedProcessors))
            processor.handleControl(control);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        pressedKeys.remove(keycode);
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
