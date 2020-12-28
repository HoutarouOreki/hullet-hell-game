package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class FadeInMusicAction extends ScriptedAction {
    private float duration;

    @Override
    protected void performAction() {
        HulletHellGame.getMusicManager().fadeIn(duration);
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }

    @Override
    protected void addArgumentsInfo() {
        addDurationArg(this::setDuration, true);
    }

    private void setDuration(float duration) {
        this.duration = duration;
    }
}
