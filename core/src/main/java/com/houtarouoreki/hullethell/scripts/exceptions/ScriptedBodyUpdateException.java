package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.scripts.ScriptedBody;

public class ScriptedBodyUpdateException extends ScriptException {
    public ScriptedBodyUpdateException(ScriptedBody scriptedBody, Throwable cause) {
        super("Exception during scripted body's \"" + scriptedBody.name + "\" update.", cause);
    }
}
