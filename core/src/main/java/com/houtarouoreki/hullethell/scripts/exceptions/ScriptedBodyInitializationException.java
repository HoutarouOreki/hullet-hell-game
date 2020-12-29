package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;

public class ScriptedBodyInitializationException extends ScriptException {
    public ScriptedBodyInitializationException(ScriptedBodyConfiguration conf, Throwable cause) {
        super("Failed to initialize scripted body \"" + conf.name + "\".", cause);
    }
}
