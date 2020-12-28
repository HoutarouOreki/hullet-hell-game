package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.scripts.ScriptedStageManager;

public class ScriptedStageUpdateException extends ScriptException {
    public ScriptedStageUpdateException(ScriptedStageManager scriptedStageManager, Throwable cause) {
        super("Exception during scripted stage's \"" +
                scriptedStageManager.name + "\" update.", cause);
    }
}
