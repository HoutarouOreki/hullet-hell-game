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
import com.houtarouoreki.hullethell.graphics.Fonts;
import com.houtarouoreki.hullethell.graphics.SpriteInfo;
import com.houtarouoreki.hullethell.input.InputManager;
import com.houtarouoreki.hullethell.numbers.Vector2;
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
import java.util.Collections;
import java.util.List;

public class HulletHellGame extends ScreenBasedGame {
    public static final String GAME_IDENTIFIER = "com.houtarouoreki.hullethell";
    private static AssetManager assetManager;
    private static InputManager inputManager;
    private static MusicManager musicManager;
    private static SoundManager soundManager;
    private static ScreenManager<GameScreen> screenManager;
    private static Settings settings;
    private static PlayerState playerState;
    private final WindowSizeContainer container;
    private Label fpsText;

    public HulletHellGame() {
        settings = new Settings();
        playerState = new PlayerState();
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

    public static PlayerState getPlayerState() {
        return playerState;
    }

    @Override
    public void initialise() {
        loadSettings();
        loadPlayerState();

        SongNotification notification = new SongNotification(assetManager);
        musicManager = new MusicManager(assetManager, notification);
        container.add(notification);

        FileHandleResolver fileHandleResolver
                = new FallbackFileHandleResolver(
                new ClasspathFileHandleResolver(),
                new InternalFileHandleResolver());

        screenManager = getScreenManager();

        assetManager.setLoader(BodyConfiguration.class,
                new BodyConfigurationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(ExplosiveConfiguration.class,
                new ExplosiveConfigurationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(ShipConfiguration.class,
                new ShipConfigurationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(SongConfiguration.class,
                new SongConfigurationLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(SpriteInfo.class,
                new SpriteInfoLoader(new InternalFileHandleResolver()));
        assetManager.setLoader(StageConfiguration.class,
                new StageConfigurationLoader(new InternalFileHandleResolver()));

        assetManager.setLoader(UiTheme.class, new UiThemeLoader(fileHandleResolver));
        assetManager.load(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class);
        assetManager.load("ui/songNotification.png", Texture.class);
        assetManager.load("ui/dialogueOverlay.png", Texture.class);
        assetManager.load("logo-black-transparent.png", Texture.class);

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
                "One Man Symphony - Beat 02",
                "One Man Symphony - First Night In Space",
                "One Man Symphony - Into The Unknown",
                "One Man Symphony - Ambush At The Dawn",
                "One Man Symphony - Transmission"
        ));

        loadItems(Arrays.asList(
                "copperOre",
                "ironOre",
                "hologramCoin"
        ));

//        loadConfigsAndTextures(assetManager, "environmentals", Arrays.asList(
//                "asteroid-large", "asteroid-medium"));

        //assetManager.load("environmentals/asteroid-large.png", Texture.class);
        loadSpriteInfo("environmentals/asteroid");
        loadSpriteInfo("lasers/laser");
        assetManager.load("lasers/laser-head.png", Texture.class);
        assetManager.load("lasers/laser-tail.png", Texture.class);

        loadConfigsAndTextures("bullets", Arrays.asList(
                "Bullet 1", "Player bullet 1",
                "hologramBullet"));

        loadExplosives(assetManager, Collections.singletonList("Explosive"));
        loadShips(Arrays.asList(
                "Enemy ship 1", "Copper eye",
                "Ship 1",
                "Beetle 1",
                "Thomson 2"
                ));

        loadEffects(assetManager, Arrays.asList(
                "blurredCircle",
                "star"
        ));
        loadSpriteInfo("effects/Explosion");

        //loadCharacters(assetManager, Arrays.asList("Temp"));

        loadStages(assetManager, Arrays.asList("Stage 1", "Stage 2",
                "Act 1",
                "Act 2a",
                "Act 2b",
                "Act 3"
        ));

        Gdx.input.setInputProcessor(inputManager);

        fpsText = new Label();
        fpsText.setPosition(new Vector2(0, 2));

        this.addScreen(new LoadingScreen());

        addSettingsListeners();
    }

    private void loadSettings() {
        try {
            if (!Mdx.playerData.hasFile("settings.json"))
                Mdx.playerData.writeJson(new SerializableSettings(),
                        "settings.json");
            settings.setSettings(Mdx.playerData
                    .readJson(SerializableSettings.class, "settings.json"));
        } catch (PlayerDataException e) {
            e.printStackTrace();
        }
    }

    private void loadPlayerState() {
        try {
            if (!Mdx.playerData.hasFile("playerState.json"))
                Mdx.playerData.writeJson(new SerializablePlayerState(playerState),
                        "playerState.json");
            playerState.set(Mdx.playerData
                    .readJson(SerializablePlayerState.class, "playerState.json"));
        } catch (PlayerDataException e) {
            e.printStackTrace();
        }
    }

    private void addSettingsListeners() {
        settings.fullScreen.addListener((oldValue, newValue) -> {
            com.badlogic.gdx.Graphics.DisplayMode dm = Gdx.graphics.getDisplayMode();
            if (newValue)
                Gdx.graphics.setFullscreenMode(dm);
            else
                Gdx.graphics.setWindowedMode(dm.width - 10, dm.height - 100);
            inputManager.clearPressedKeys();
        }, true);
        settings.renderFPS.addListener(
                (oldValue, newValue) -> fpsText.setVisibility(newValue),
                true);
    }

    private void loadStages(AssetManager assetManager, List<String> names) {
        for (String name : names) {
            assetManager.load("stages/" + name + ".hhc", StageConfiguration.class);
        }
    }

    private void loadSounds(AssetManager am, List<String> names) {
        for (String name : names) {
            am.load("sounds/" + name + ".mp3", Sound.class);
        }
    }

    private void loadItems(List<String> names) {
        for (String name : names) {
            loadSpriteInfo("items/" + name);
        }
    }

    private void loadMusicAndConfigs(AssetManager am, List<String> names) {
        for (String name : names) {
            am.load("music/" + name + ".mp3", Music.class);
            am.load("music/" + name + ".hhc", SongConfiguration.class);
        }
    }

    private void loadSpriteInfo(String name) {
        assetManager.load(name + "-sprite.hhc", SpriteInfo.class);
    }

    private void loadConfigsAndTextures(String folder, List<String> names) {
        for (String name : names) {
            String path = folder + "/" + name;
            assetManager.load(path + ".png", Texture.class);
            assetManager.load(path + ".hhc", BodyConfiguration.class);
            loadSpriteInfo(path);
        }
    }

    private void loadExplosives(AssetManager am, List<String> names) {
        for (String name : names) {
            String path = "explosives/" + name;
            am.load(path + ".png", Texture.class);
            am.load(path + ".hhc", ExplosiveConfiguration.class);
            loadSpriteInfo(path);
        }
    }

    private void loadShips(List<String> names) {
        for (String name : names) {
            String path = "ships/" + name;
            assetManager.load(path + ".png", Texture.class);
            assetManager.load(path + ".hhc", ShipConfiguration.class);
            loadSpriteInfo(path);
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
