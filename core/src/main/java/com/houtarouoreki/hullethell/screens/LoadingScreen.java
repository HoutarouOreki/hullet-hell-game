package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.screens.garage.GarageScreen;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

public class LoadingScreen extends HulletHellScreen {
    private final float minimum_loading_time = 2.5f;
    private float loadingDuration = 0;
    private boolean beganTransition = false;
    private Texture logo;

    @Override
    public void initialise(GameContainer gc) {
        logo = new Texture("logo.png");
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager,
                       float delta) {
        loadingDuration += delta;
        if (beganTransition)
            return;

        if (HulletHellGame.getAssetManager().update() && loadingDuration > minimum_loading_time) {
            HulletHellGame.getScreensManager().addGameScreen(new MainMenuScreen());
            HulletHellGame.getScreensManager().addGameScreen(new GarageScreen());
            PlayScreen ps = new PlayScreen();
            ps.setStage(HulletHellGame.getAssetManager().get("stages/Stage 1.cfg"));
            HulletHellGame.getScreensManager().addGameScreen(ps);
            HulletHellGame.getScreensManager().addGameScreen(new ResultsScreen());
            HulletHellGame.getScreensManager().addGameScreen(new SettingsScreen());
            HulletHellGame.getScreensManager().addGameScreen(new LevelSelectScreen());
            screenManager.enterGameScreen(1, new FadeOutTransition(), new FadeInTransition());
            beganTransition = true;
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        float screenCenterH = gc.getWidth() / 2f;
        float screenCenterV = gc.getHeight() / 2f;
        g.drawTexture(logo, screenCenterH - 120, screenCenterV - 180, 240, 240);

        g.drawString("Loading game", screenCenterH, screenCenterV + 60,
                0, Align.center);
        g.setColor(Color.FOREST);
        g.fillRect(screenCenterH / 2, screenCenterV + 100,
                getProgressBarPercentage() * screenCenterH, 30);
    }

    @Override
    public int getId() {
        return LOADING_SCREEN;
    }

    private float getProgressBarPercentage() {
        return Math.min(HulletHellGame.getAssetManager().getProgress(),
                loadingDuration / (minimum_loading_time - 0.5f));
    }
}
