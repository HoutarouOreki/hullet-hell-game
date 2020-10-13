package com.houtarouoreki.hullethell;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.houtarouoreki.hullethell.screens.LoadingScreen;
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
        assetManager.load("playerShip.png", Texture.class);
        assetManager.load("enemyShip1.png", Texture.class);
        assetManager.load("asteroida.png", Texture.class);
        assetManager.load("bullet1.png", Texture.class);

        this.addScreen(new LoadingScreen(this, assetManager));
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
