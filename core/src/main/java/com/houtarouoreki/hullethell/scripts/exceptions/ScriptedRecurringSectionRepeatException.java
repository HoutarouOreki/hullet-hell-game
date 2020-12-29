package com.houtarouoreki.hullethell.scripts.exceptions;

import com.houtarouoreki.hullethell.scripts.ScriptedRecurringSection;

public class ScriptedRecurringSectionRepeatException extends ScriptException{
    public ScriptedRecurringSectionRepeatException(ScriptedRecurringSection section, Throwable cause) {
        super("Failed to initialize repeated subsection in recurring scripted section \"" +
                section.name + "\".", cause);
    }
}
