package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionStringArg;

public class LoopSongAction extends ScriptedAction {
    private String songPath;

    @Override
    protected void performAction() {
        HulletHellGame.getMusicManager().setCurrentSong(songPath);
        HulletHellGame.getMusicManager().setLooping(true);
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
                "Trick id",
                null,
                "One Man Symphony - First Night In Space",
                match_everything_pattern,
                this::setSongPath,
                false
        ));
    }

    private void setSongPath(String songPath) {
        this.songPath = songPath;
    }
}
