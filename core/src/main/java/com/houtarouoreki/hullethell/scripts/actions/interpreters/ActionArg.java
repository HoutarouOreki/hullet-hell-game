package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Pattern;

public class ActionArg<E, T extends ActionArgsParserSetter<E>> {
    public final String name;
    public final String description;
    public final String example;
    public final boolean optional;
    public final Pattern pattern;
    public final T callback;

    public ActionArg(String name, String description, String example, Pattern pattern, T callback, boolean optional) {
        this.name = name;
        this.description = description;
        this.example = example;
        this.pattern = pattern;
        this.callback = callback;
        this.optional = optional;
    }

    public ActionArg(String name, String description, String example, Pattern pattern, T callback) {
        this(name, description, example, pattern, callback, false);
    }
}
