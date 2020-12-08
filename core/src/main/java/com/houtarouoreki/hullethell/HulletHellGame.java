package com.houtarouoreki.hullethell;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.houtarouoreki.hullethell.configurations.*;
import com.houtarouoreki.hullethell.music.MusicManager;
import com.houtarouoreki.hullethell.screens.LoadingScreen;
import org.mini2Dx.core.assets.FallbackFileHandleResolver;
import org.mini2Dx.core.game.ScreenBasedGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.ui.UiThemeLoader;
import org.mini2Dx.ui.style.UiTheme;

import java.util.Arrays;
import java.util.List;

public class HulletHellGame extends ScreenBasedGame {
    public static final String GAME_IDENTIFIER = "com.houtarouoreki.hullethell";
    private MusicManager musicManager;
    private AssetManager assetManager;

    @Override
    public void initialise() {
        FileHandleResolver fileHandleResolver = new FallbackFileHandleResolver(new ClasspathFileHandleResolver(), new InternalFileHandleResolver());
        assetManager = new AssetManager();
        musicManager = new MusicManager(assetManager);

        assetManager.setLoader(BodyConfiguration.class, new BodyConfigurationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(SongConfiguration.class, new SongConfigurationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(StageConfiguration.class, new StageConfigurationLoader(new InternalFileHandleResolver()));

        assetManager.setLoader(UiTheme.class, new UiThemeLoader(fileHandleResolver));
        assetManager.load(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class);

        loadMusicAndConfigs(assetManager, Arrays.asList("To Eris - Social Blast"));

        loadConfigsAndTextures(assetManager, "environmentals", Arrays.asList("Asteroid"));
        loadConfigsAndTextures(assetManager, "bullets", Arrays.asList("Bullet 1", "Player bullet 1"));
        loadConfigsAndTextures(assetManager, "ships", Arrays.asList("Enemy ship 1", "Ship 1", "Copper eye"));

        loadStages(assetManager, Arrays.asList("Stage 1"));

        this.addScreen(new LoadingScreen(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    private void loadStages(AssetManager assetManager, List<String> names) {
        for (String name : names) {
            assetManager.load("stages/" + name + ".cfg", StageConfiguration.class);
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
    }

    @Override
    public void interpolate(float alpha) {
        getScreenManager().interpolate(this, alpha);
    }

    @Override
    public void render(Graphics g) {
        getScreenManager().render(this, g);
        musicManager.render(g);
    }

    @Override
    public int getInitialScreenId() {
        return 0;
    }
}
