package com.houtarouoreki.hullethell;

import com.houtarouoreki.hullethell.bindables.Bindable;
import com.houtarouoreki.hullethell.configurations.SerializablePlayerState;

import java.util.HashSet;

public class PlayerState {
    public final HashSet<String> unlockedShips = new HashSet<>();
    public Bindable<String> currentShipFileName = new Bindable<>("Ship 1");

    public PlayerState() {
        unlockedShips.add(currentShipFileName.getValue());
    }

    public void set(SerializablePlayerState data) {
        unlockedShips.clear();
        unlockedShips.addAll(data.unlockedShips);
        currentShipFileName.setValue(data.currentShip);
        unlockedShips.add("Beetle 1");
        unlockedShips.add("Thomson 2");
    }
}
