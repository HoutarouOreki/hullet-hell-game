package com.houtarouoreki.hullethell.screens;

import com.houtarouoreki.hullethell.HulletHellGame;
import org.mini2Dx.core.screen.BasicGameScreen;

public abstract class HulletHellScreen extends BasicGameScreen {
    protected final HulletHellGame game;

    public HulletHellScreen(HulletHellGame game) {
        this.game = game;
    }
}
