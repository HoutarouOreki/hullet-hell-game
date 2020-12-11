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
import com.houtarouoreki.hullethell.configurations.*;
import com.houtarouoreki.hullethell.input.InputManager;
import com.houtarouoreki.hullethell.screens.LoadingScreen;
import com.houtarouoreki.hullethell.ui.WindowSizeContainer;
import org.mini2Dx.core.assets.FallbackFileHandleResolver;
import org.mini2Dx.core.game.ScreenBasedGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.ui.UiThemeLoader;
import org.mini2Dx.ui.style.UiTheme;

import java.util.Arrays;
import java.util.List;

public class HulletHellGame extends ScreenBasedGame {
    public static final String GAME_IDENTIFIER = "com.houtarouoreki.hullethell";
    private WindowSizeContainer container;
    private AssetManager assetManager;
    private InputManager inputManager;
    private MusicManager musicManager;
    private SoundManager soundManager;

    @Override
    public void initialise() {
        FileHandleResolver fileHandleResolver
                = new FallbackFileHandleResolver(
                        new ClasspathFileHandleResolver(),
                        new InternalFileHandleResolver());
        container = new WindowSizeContainer();
        assetManager = new AssetManager();
        SongNotification notification = new SongNotification(assetManager);
        inputManager = new InputManager();
        musicManager = new MusicManager(assetManager, notification);
        soundManager = new SoundManager(assetManager);
        container.add(notification);

        assetManager.setLoader(BodyConfiguration.class, new BodyConfigurationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(SongConfiguration.class, new SongConfigurationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(StageConfiguration.class, new StageConfigurationLoader(new InternalFileHandleResolver()));

        assetManager.setLoader(UiTheme.class, new UiThemeLoader(fileHandleResolver));
        assetManager.load(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class);
        assetManager.load("ui/songNotification.png", Texture.class);

        loadSounds(assetManager, Arrays.asList("laser1", "laser2", "softExpl1"));
        loadMusicAndConfigs(assetManager, Arrays.asList(
                "To Eris - Social Blast",
                "One Man Symphony - Beat 02"
        ));

        loadConfigsAndTextures(assetManager, "environmentals", Arrays.asList("Asteroid"));
        loadConfigsAndTextures(assetManager, "bullets", Arrays.asList("Bullet 1", "Player bullet 1"));
        loadConfigsAndTextures(assetManager, "ships", Arrays.asList("Enemy ship 1", "Ship 1", "Copper eye"));

        loadStages(assetManager, Arrays.asList("Stage 1"));

        Gdx.input.setInputProcessor(inputManager);

        this.addScreen(new LoadingScreen(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
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
        getScreenManager().update(this, delta);
        musicManager.update(delta);
        container.update(delta);
    }

    @Override
    public void interpolate(float alpha) {
        getScreenManager().interpolate(this, alpha);
    }

    @Override
    public void render(Graphics g) {
        getScreenManager().render(this, g);
        container.render(g);
    }

    @Override
    public int getInitialScreenId() {
        return 0;
    }
}
