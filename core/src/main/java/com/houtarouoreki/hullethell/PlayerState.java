package com.houtarouoreki.hullethell;

import com.houtarouoreki.hullethell.bindables.Bindable;
import com.houtarouoreki.hullethell.configurations.SerializablePlayerState;
import com.houtarouoreki.hullethell.configurations.ShipConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    public List<ShipConfiguration> getUnlockedShipConfigurations() {
        List<ShipConfiguration> shipConfigurations = new ArrayList<>();
        for (String unlockedShip : unlockedShips) {
            shipConfigurations.add(HulletHellGame.getAssetManager()
                    .get("ships/" + unlockedShip + ".cfg"));
        }
        return shipConfigurations;
    }

    public ShipConfiguration getCurrentShipConfiguration() {
        return HulletHellGame.getAssetManager()
                .get("ships/" + currentShipFileName.getValue() + ".cfg");
    }
}
