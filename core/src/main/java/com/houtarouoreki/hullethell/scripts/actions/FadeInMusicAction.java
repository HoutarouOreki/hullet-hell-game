package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class FadeInMusicAction extends ScriptedAction {
    private double duration;

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        duration = Double.parseDouble(arguments.get(0));
    }

    @Override
    protected void performAction() {
        HulletHellGame.getMusicManager().fadeIn((float) duration);
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
