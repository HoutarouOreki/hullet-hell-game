package com.houtarouoreki.hullethell;

import com.houtarouoreki.hullethell.bindables.Bindable;
import com.houtarouoreki.hullethell.configurations.SerializablePlayerState;

import java.util.HashSet;

public class PlayerState {
    public final HashSet<String> unlockedShips = new HashSet<>();
    public Bindable<String> currentShip = new Bindable<>("Ship 1");

    public PlayerState() {
        unlockedShips.add(currentShip.getValue());
    }

    public void set(SerializablePlayerState data) {
        unlockedShips.clear();
        unlockedShips.addAll(data.unlockedShips);
        currentShip.setValue(data.currentShip);
    }
}
