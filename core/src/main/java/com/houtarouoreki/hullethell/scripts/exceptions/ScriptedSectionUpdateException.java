package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.scripts.ScriptedSection;

public class ScriptedSectionUpdateException extends ScriptException {
    public ScriptedSectionUpdateException(ScriptedSection scriptedSection, Throwable cause) {
        super("Updating scripted section \"" + scriptedSection.name + "\" failed.", cause);
    }
}
