package com.houtarouoreki.hullethell.desktop;

import org.mini2Dx.desktop.DesktopMini2DxConfig;

import com.badlogic.gdx.backends.lwjgl.DesktopMini2DxGame;

import com.houtarouoreki.hullethell.HulletHellGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		DesktopMini2DxConfig config = new DesktopMini2DxConfig(HulletHellGame.GAME_IDENTIFIER);
		config.title = "Hullet Hell";
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = true;
		new DesktopMini2DxGame(new HulletHellGame(), config);
	}
}
