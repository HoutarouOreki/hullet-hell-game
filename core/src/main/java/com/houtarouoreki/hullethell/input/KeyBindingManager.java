package com.houtarouoreki.hullethell.input;

import com.badlogic.gdx.Input;

import java.util.*;

public class KeyBindingManager {
    private final Dictionary<Controls, HashSet<Integer>> controlKeys;
    private Dictionary<Integer, Controls> keyControls;

    public KeyBindingManager() {
        controlKeys = new Hashtable<Controls, HashSet<Integer>>();
        for (Controls control : Controls.values()) {
            controlKeys.put(control, getDefaultKeyBinding(control));
        }
        generateKeyActions();
    }

    public Controls getControlFromKey(int key) {
        return keyControls.get(key);
    }

    public HashSet<Integer> getControlKeys(Controls control) {
        return controlKeys.get(control);
    }

    private HashSet<Integer> getDefaultKeyBinding(Controls a) {
        HashSet<Integer> set = new HashSet<Integer>();
        switch (a) {
            case left:
                set.add(Input.Keys.LEFT);
                set.add(Input.Keys.A);
                break;
            case right:
                set.add(Input.Keys.RIGHT);
                set.add(Input.Keys.D);
                break;
            case up:
                set.add(Input.Keys.UP);
                set.add(Input.Keys.W);
                break;
            case down:
                set.add(Input.Keys.DOWN);
                set.add(Input.Keys.S);
                break;
            case shoot:
                set.add(Input.Keys.SPACE);
                break;
            case select:
                set.add(Input.Keys.ENTER);
                set.add(Input.Keys.SPACE);
                break;
            case back:
                set.add(Input.Keys.ESCAPE);
                set.add(Input.Keys.BACKSPACE);
                break;
        }
        return set;
    }

    private void generateKeyActions() {
        keyControls = new Hashtable<Integer, Controls>();
        for (Controls control : Controls.values()) {
            for (int key : controlKeys.get(control)) {
                keyControls.put(key, control);
            }
        }
    }
}
