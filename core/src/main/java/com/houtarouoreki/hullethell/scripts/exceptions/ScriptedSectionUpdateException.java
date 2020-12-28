package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.scripts.ScriptedSection;

public class ScriptedSectionUpdateException extends ScriptException {
    public ScriptedSectionUpdateException(ScriptedSection scriptedSection, Throwable cause) {
        super("Exception during scripted section's \"" +
                scriptedSection.name + "\" update.", cause);
    }
}
