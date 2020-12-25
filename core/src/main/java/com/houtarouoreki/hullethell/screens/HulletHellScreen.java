package com.houtarouoreki.hullethell.screens;

import com.houtarouoreki.hullethell.ui.WindowSizeContainer;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;

public abstract class HulletHellScreen extends BasicGameScreen {
    public static final int LOADING_SCREEN = 0;
    public static final int MAIN_MENU_SCREEN = 1;
    public static final int PLAY_SCREEN = 2;
    public static final int RESULTS_SCREEN = 3;
    public static final int SETTINGS_SCREEN = 4;
    public static final int LEVEL_SELECT_SCREEN = 5;
    public static final int GARAGE_SCREEN = 6;
    public final WindowSizeContainer container = new WindowSizeContainer();

    @Override
    public void initialise(GameContainer gc) {
    }

    @Override
    public final void interpolate(GameContainer gc, float alpha) {
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        container.render(g);
    }

    @Override
    public void update(GameContainer gc,
                       ScreenManager<? extends GameScreen> screenManager, float delta) {
        container.update(delta);
    }
}
