package com.houtarouoreki.hullethell.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.DesktopMini2DxGame;
import com.houtarouoreki.hullethell.HulletHellGame;
import org.mini2Dx.desktop.DesktopMini2DxConfig;

public class DesktopLauncher {
    public static void main(String[] arg) {
        DesktopMini2DxConfig config = new DesktopMini2DxConfig(HulletHellGame.GAME_IDENTIFIER);
        config.title = "Hullet Hell";
        config.width = 1280;
        config.height = 600;
        config.vSyncEnabled = true;

        config.targetFPS = 0;
        // z jakiegoś powodu przeciwdziała tearingowi i przycinaniu się
        // zakładając, że ustawienia z panelu NVIDIA niczego nie forsują
        // (np. 3D Settings -> Adjust image settings -> Performance)

        config.fullscreen = false;
        config.samples = 0;
        DesktopMini2DxGame game = new DesktopMini2DxGame(new HulletHellGame(), config);
        //game.getGraphics().setFullscreenMode(Gdx.graphics.getDisplayMode());
    }
}
