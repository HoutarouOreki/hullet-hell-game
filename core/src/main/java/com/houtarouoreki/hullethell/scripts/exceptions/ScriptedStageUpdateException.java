package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.scripts.ScriptedStageManager;

public class ScriptedStageUpdateException extends ScriptException {
    public ScriptedStageUpdateException(ScriptedStageManager scriptedStageManager, Throwable cause) {
        super("Updating scripted stage \"" + scriptedStageManager.name + "\" failed.", cause);
    }
}
