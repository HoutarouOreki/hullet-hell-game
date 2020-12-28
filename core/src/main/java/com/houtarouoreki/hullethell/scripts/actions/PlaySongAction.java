package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionStringArg;

public class PlaySongAction extends ScriptedAction {
    private String songName;

    @Override
    protected void performAction() {
        HulletHellGame.getMusicManager().setCurrentSong(songName);
        HulletHellGame.getMusicManager().play();
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }

    @Override
    protected void addArgumentsInfo() {
        parser.stringArgs.add(new ActionStringArg(
                "Track",
                "",
                "One Man Symphony - Attack Reversal",
                match_everything_pattern,
                this::setSongName,
                false
        ));
    }

    private void setSongName(String s) {
        songName = s;
    }
}
