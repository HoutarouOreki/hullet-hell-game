package com.houtarouoreki.hullethell;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.houtarouoreki.hullethell.screens.LoadingScreen;
import com.houtarouoreki.hullethell.screens.MainMenuScreen;
import com.houtarouoreki.hullethell.screens.PlayScreen;
import org.mini2Dx.core.assets.FallbackFileHandleResolver;
import org.mini2Dx.core.game.ScreenBasedGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.ui.UiThemeLoader;
import org.mini2Dx.ui.style.UiTheme;

public class HulletHellGame extends ScreenBasedGame {
    public static final String GAME_IDENTIFIER = "com.houtarouoreki.hullethell";

    @Override
    public void initialise() {
        FileHandleResolver fileHandleResolver = new FallbackFileHandleResolver(new ClasspathFileHandleResolver(), new InternalFileHandleResolver());
        AssetManager assetManager = new AssetManager();

        assetManager.setLoader(UiTheme.class, new UiThemeLoader(fileHandleResolver));
        assetManager.load(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class);

        this.addScreen(new LoadingScreen(assetManager));
        this.addScreen(new MainMenuScreen(assetManager));
        this.addScreen(new PlayScreen());
    }

    @Override
    public void update(float delta) {
        getScreenManager().update(this, delta);
    }

    @Override
    public void interpolate(float alpha) {
        getScreenManager().interpolate(this, alpha);
    }

    @Override
    public void render(Graphics g) {
        getScreenManager().render(this, g);
    }

    @Override
    public int getInitialScreenId() {
        return 0;
    }
}
