package com.houtarouoreki.hullethell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.audio.MusicManager;
import com.houtarouoreki.hullethell.audio.SongNotification;
import com.houtarouoreki.hullethell.audio.SoundManager;
import com.houtarouoreki.hullethell.bindables.ValueChangeListener;
import com.houtarouoreki.hullethell.configurations.*;
import com.houtarouoreki.hullethell.graphics.Fonts;
import com.houtarouoreki.hullethell.input.InputManager;
import com.houtarouoreki.hullethell.screens.LoadingScreen;
import com.houtarouoreki.hullethell.ui.Label;
import com.houtarouoreki.hullethell.ui.WindowSizeContainer;
import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.assets.FallbackFileHandleResolver;
import org.mini2Dx.core.game.ScreenBasedGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.playerdata.PlayerDataException;
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
    private Label fpsText;

    public HulletHellGame() {
        settings = new Settings();
        container = new WindowSizeContainer();
        assetManager = new AssetManager();
        inputManager = new InputManager();
        soundManager = new SoundManager(assetManager);
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
        loadSettings();

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
        assetManager.load("ui/dialogueOverlay.png", Texture.class);

        loadSounds(assetManager, Arrays.asList(
                "laser1",
                "laser2",
                "softExpl1",
                "button1",
                "button2",
                "noteLow",
                "noteHigh",
                "kick-gritty",
                "hihat-808",
                "collision-loop"
        ));
        loadMusicAndConfigs(assetManager, Arrays.asList(
                "To Eris - Social Blast",
                "One Man Symphony - Beat 02"
        ));

        loadItems(assetManager, Arrays.asList(
                "copperOre",
                "ironOre"
        ));

        loadConfigsAndTextures(assetManager, "environmentals", Arrays.asList("Asteroid"));
        loadConfigsAndTextures(assetManager, "bullets", Arrays.asList("Bullet 1", "Player bullet 1"));
        loadConfigsAndTextures(assetManager, "ships", Arrays.asList("Enemy ship 1", "Ship 1", "Copper eye"));

        loadEffects(assetManager, Arrays.asList(
                "blurredCircle",
                "star"
                ));
        loadAnimatedEffect(assetManager, "Explosion", 6);

        loadCharacters(assetManager, Arrays.asList("Temp"));

        loadStages(assetManager, Arrays.asList("Stage 1", "Stage 2",
                "Act 1"));

        Gdx.input.setInputProcessor(inputManager);

        fpsText = new Label();
        fpsText.setPosition(new Vector2(0, 2));

        this.addScreen(new LoadingScreen());

        addSettingsListeners();
    }

    private void loadSettings() {
        try {
            if (!Mdx.playerData.hasFile("playerData.json"))
                Mdx.playerData.writeJson(new SerializableSettings(), "playerData.json");
            settings.setSettings(Mdx.playerData
                    .readJson(SerializableSettings.class, "playerData.json"));
        } catch (PlayerDataException e) {
            e.printStackTrace();
        }
    }

    private void addSettingsListeners() {
        settings.fullScreen.addListener(new ValueChangeListener<Boolean>() {
            @Override
            public void onValueChanged(Boolean oldValue, Boolean newValue) {
                com.badlogic.gdx.Graphics.DisplayMode dm = Gdx.graphics.getDisplayMode();
                if (newValue)
                    Gdx.graphics.setFullscreenMode(dm);
                else
                    Gdx.graphics.setWindowedMode(dm.width - 10, dm.height - 100);
                inputManager.clearPressedKeys();
            }
        }, true);
        settings.renderFPS.addListener(new ValueChangeListener<Boolean>() {
            @Override
            public void onValueChanged(Boolean oldValue, Boolean newValue) {
                fpsText.setVisibility(newValue);
            }
        }, true);
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

    private void loadItems(AssetManager am, List<String> names) {
        for (String name : names) {
            am.load("items/" + name + ".png", Texture.class);
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

    private void loadEffects(AssetManager am, List<String> names) {
        for (String name : names) {
            am.load("effects/" + name + ".png", Texture.class);
        }
    }

    private void loadCharacters(AssetManager am, List<String> names) {
        for (String name : names) {
            am.load("characters/" + name + ".png", Texture.class);
        }
    }

    private void loadAnimatedEffect(AssetManager am, String name, int frames) {
        for (int i = 0; i < frames; i++) {
            am.load("effects/" + name + "-" + i + ".png", Texture.class);
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
        if (Fonts.defaultFont == null) {
            Fonts.defaultFont = g.getFont();
            fpsText.font = g.getFont();
            container.add(fpsText);
        }
        fpsText.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        getScreensManager().render(this, g);
        g.setTranslation(0, 0);
        g.clearScaling();
        g.clearBlendFunction();
        g.clearShaderProgram();
        g.removeClip();
        container.render(g);
    }

    @Override
    public int getInitialScreenId() {
        return 0;
    }
}
