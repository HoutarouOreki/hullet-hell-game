package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class ScriptedActionInitializationException extends ScriptException {
    public ScriptedActionInitializationException(ScriptedAction scriptedAction, Throwable cause) {
        super("Initialization of action " + scriptedAction.type + " failed.\n" +
                "Line " + scriptedAction.scriptFileLineNumber + '\n' +
                "Arguments: " + scriptedAction.arguments, cause);
    }
}
