package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class PlaySongAction extends ScriptedAction {
    @Override
    protected void performAction() {
        HulletHellGame.getMusicManager().setCurrentSong(arguments.get(0));
        HulletHellGame.getMusicManager().play();
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
