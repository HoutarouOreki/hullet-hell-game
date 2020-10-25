package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;
import com.houtarouoreki.hullethell.configurations.Configurations;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

import java.util.Arrays;

public class LoadingScreen extends BasicGameScreen {
    private final HulletHellGame game;
    private final AssetManager assetManager;
    private final float minimum_loading_time = 2.5f;
    private final Configurations configurations;
    private float loadingDuration = 0;
    private boolean beganTransition = false;
    private Texture logo;

    public LoadingScreen(HulletHellGame game, AssetManager assetManager, Configurations configurations) {
        this.game = game;
        this.assetManager = assetManager;
        this.configurations = configurations;
    }

    @Override
    public void initialise(GameContainer gc) {
        logo = new Texture("logo.png");
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager, float delta) {
        loadingDuration += delta;
        if (beganTransition)
            return;

        if (assetManager.update() && loadingDuration > minimum_loading_time) {
            game.addScreen(new MainMenuScreen(assetManager, screenManager));
            game.addScreen(new PlayScreen(assetManager, configurations, screenManager));
            screenManager.enterGameScreen(1, new FadeOutTransition(), new FadeInTransition());
            beganTransition = true;
        }
    }

    @Override
    public void interpolate(GameContainer gc, float alpha) {
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        float screenCenterH = gc.getWidth() / 2f;
        float screenCenterV = gc.getHeight() / 2f;
        g.drawTexture(logo, screenCenterH - 120, screenCenterV - 180, 240, 240);
        g.drawString("Loading game", screenCenterH, screenCenterV + 60, 0, Align.center);
        g.setColor(Color.FOREST);
        g.fillRect(screenCenterH / 2, screenCenterV + 100, getProgressBarPercentage() * screenCenterH, 30);
    }

    @Override
    public int getId() {
        return 0;
    }

    private float getProgressBarPercentage() {
        return Math.min(assetManager.getProgress(), loadingDuration / (minimum_loading_time - 0.5f));
    }
}
