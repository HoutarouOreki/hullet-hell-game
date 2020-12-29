package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;

public class ActionTypeNotFoundException extends ScriptException {
    public ActionTypeNotFoundException(ScriptedActionConfiguration actionConfiguration) {
        super("Action type \"" + actionConfiguration.type + "\" not found.\n" +
                "Line: " + actionConfiguration.scriptFileLineNumber);
    }
}
