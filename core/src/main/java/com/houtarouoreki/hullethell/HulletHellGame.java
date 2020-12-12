package com.houtarouoreki.hullethell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.houtarouoreki.hullethell.audio.MusicManager;
import com.houtarouoreki.hullethell.audio.SongNotification;
import com.houtarouoreki.hullethell.audio.SoundManager;
import com.houtarouoreki.hullethell.bindables.ValueChangeListener;
import com.houtarouoreki.hullethell.configurations.*;
import com.houtarouoreki.hullethell.graphics.Fonts;
import com.houtarouoreki.hullethell.input.InputManager;
import com.houtarouoreki.hullethell.screens.LoadingScreen;
import com.houtarouoreki.hullethell.ui.WindowSizeContainer;
import org.mini2Dx.core.assets.FallbackFileHandleResolver;
import org.mini2Dx.core.game.ScreenBasedGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.ui.UiThemeLoader;
import org.mini2Dx.ui.style.UiTheme;

import java.util.Arrays;
import java.util.List;

public class HulletHellGame extends ScreenBasedGame {
    public static final String GAME_IDENTIFIER = "com.houtarouoreki.hullethell";
    private static AssetManager assetManager;
    private static InputManager inputManager;
    private static MusicManager musicManager;
    private static SoundManager soundManager;
    private static ScreenManager<GameScreen> screenManager;
    private static Settings settings;
    private final WindowSizeContainer container;

    public HulletHellGame() {
        settings = new Settings();
        container = new WindowSizeContainer();
        assetManager = new AssetManager();
        inputManager = new InputManager();
        soundManager = new SoundManager(assetManager);
        settings.fullScreen.addListener(new ValueChangeListener<Boolean>() {
            @Override
            public void onValueChanged(Boolean oldValue, Boolean newValue) {
                com.badlogic.gdx.Graphics.DisplayMode dm = Gdx.graphics.getDisplayMode();
                if (newValue)
                    Gdx.graphics.setFullscreenMode(dm);
                else
                    Gdx.graphics.setWindowedMode(dm.width - 10, dm.height - 100);
            }
        });
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }

    public static InputManager getInputManager() {
        return inputManager;
    }

    public static MusicManager getMusicManager() {
        return musicManager;
    }

    public static SoundManager getSoundManager() {
        return soundManager;
    }

    public static ScreenManager<GameScreen> getScreensManager() {
        return screenManager;
    }

    public static Settings getSettings() {
        return settings;
    }

    @Override
    public void initialise() {
        SongNotification notification = new SongNotification(assetManager);
        musicManager = new MusicManager(assetManager, notification);
        container.add(notification);

        FileHandleResolver fileHandleResolver
                = new FallbackFileHandleResolver(
                new ClasspathFileHandleResolver(),
                new InternalFileHandleResolver());

        screenManager = getScreenManager();

        assetManager.setLoader(BodyConfiguration.class, new BodyConfigurationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(SongConfiguration.class, new SongConfigurationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(StageConfiguration.class, new StageConfigurationLoader(new InternalFileHandleResolver()));

        assetManager.setLoader(UiTheme.class, new UiThemeLoader(fileHandleResolver));
        assetManager.load(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class);
        assetManager.load("ui/songNotification.png", Texture.class);

        loadSounds(assetManager, Arrays.asList(
                "laser1",
                "laser2",
                "softExpl1",
                "button1",
                "button2",
                "noteLow",
                "noteHigh"
        ));
        loadMusicAndConfigs(assetManager, Arrays.asList(
                "To Eris - Social Blast",
                "One Man Symphony - Beat 02"
        ));

        loadConfigsAndTextures(assetManager, "environmentals", Arrays.asList("Asteroid"));
        loadConfigsAndTextures(assetManager, "bullets", Arrays.asList("Bullet 1", "Player bullet 1"));
        loadConfigsAndTextures(assetManager, "ships", Arrays.asList("Enemy ship 1", "Ship 1", "Copper eye"));

        loadStages(assetManager, Arrays.asList("Stage 1"));

        Gdx.input.setInputProcessor(inputManager);

        this.addScreen(new LoadingScreen());
    }

    private void loadStages(AssetManager assetManager, List<String> names) {
        for (String name : names) {
            assetManager.load("stages/" + name + ".cfg", StageConfiguration.class);
        }
    }

    private void loadSounds(AssetManager am, List<String> names) {
        for (String name : names) {
            am.load("sounds/" + name + ".mp3", Sound.class);
        }
    }

    private void loadMusicAndConfigs(AssetManager am, List<String> names) {
        for (String name : names) {
            am.load("music/" + name + ".mp3", Music.class);
            am.load("music/" + name + ".cfg", SongConfiguration.class);
        }
    }

    private void loadConfigsAndTextures(AssetManager am, String folder, List<String> names) {
        for (String name : names) {
            am.load(folder + "/" + name + ".png", Texture.class);
            am.load(folder + "/" + name + ".cfg", BodyConfiguration.class);
        }
    }

    @Override
    public void update(float delta) {
        musicManager.update(delta);
        container.update(delta);
        getScreensManager().update(this, delta);
    }

    @Override
    public void interpolate(float alpha) {
        //getScreensManager().interpolate(this, alpha);
    }

    @Override
    public void render(Graphics g) {
        if (Fonts.defaultFont == null)
            Fonts.defaultFont = g.getFont();
        container.render(g);
        getScreensManager().render(this, g);
        g.clearScaling();
        g.setTranslation(0, 0);
        g.drawString("FPS: " + Gdx.graphics.getFramesPerSecond(), 0, 0);
    }

    @Override
    public int getInitialScreenId() {
        return 0;
    }
}
