package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Pattern;

public class ActionArg<E, T extends ActionArgsParserSetter<E>> extends ActionArgBase {
    public final T callback;

    public ActionArg(String name, String description, String example, Pattern pattern, T callback, boolean optional) {
        super(name, description, example, pattern, optional);
        this.callback = callback;
    }

    public ActionArg(String name, String description, String example, Pattern pattern, T callback) {
        this(name, description, example, pattern, callback, false);
    }
}
