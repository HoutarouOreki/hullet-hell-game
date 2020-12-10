package com.houtarouoreki.hullethell.screens;

import com.houtarouoreki.hullethell.HulletHellGame;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.screen.BasicGameScreen;

public abstract class HulletHellScreen extends BasicGameScreen {
    public static final int LOADING_SCREEN = 0;
    public static final int MAIN_MENU_SCREEN = 1;
    public static final int PLAY_SCREEN = 2;
    public static final int RESULTS_SCREEN = 3;
    public static final int SETTINGS_SCREEN = 4;

    protected final HulletHellGame game;

    public HulletHellScreen(HulletHellGame game) {
        this.game = game;
    }

    @Override
    public final void interpolate(GameContainer gc, float alpha) {
    }
}
