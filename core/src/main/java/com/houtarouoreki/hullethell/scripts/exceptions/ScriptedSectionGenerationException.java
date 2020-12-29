package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.scripts.ScriptedSection;

public class ScriptedSectionGenerationException extends ScriptException {
    public ScriptedSectionGenerationException(ScriptedSection section, Throwable cause) {
        super("Failed to generate body or action queue for scripted section \"" +
                section.name + "\".", cause);
    }
}
