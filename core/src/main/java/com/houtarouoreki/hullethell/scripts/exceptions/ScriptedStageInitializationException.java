package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.configurations.StageConfiguration;

public class ScriptedStageInitializationException extends ScriptException {
    public ScriptedStageInitializationException(StageConfiguration script, Throwable cause) {
        super("Failed to initialize scripted stage \"" + script.name + "\".", cause);
    }
}
