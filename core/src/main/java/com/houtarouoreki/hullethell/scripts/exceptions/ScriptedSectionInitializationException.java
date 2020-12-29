package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;

public class ScriptedSectionInitializationException extends ScriptException {
    public ScriptedSectionInitializationException(ScriptedSectionConfiguration conf, Throwable cause) {
        super("Failed to initialize scripted section \"" + conf.name + "\".", cause);
    }
}
