package com.houtarouoreki.hullethell.configurations;

import com.houtarouoreki.hullethell.PlayerState;
import org.mini2Dx.core.serialization.annotation.Field;

import java.util.ArrayList;
import java.util.List;

public class SerializablePlayerState {
    @Field
    public List<String> unlockedShips;
    @Field
    public String currentShip;

    public SerializablePlayerState() {
    }

    public SerializablePlayerState(PlayerState playerState) {
        unlockedShips = new ArrayList<>(playerState.unlockedShips);
        currentShip = playerState.currentShip.getValue();
    }
}
