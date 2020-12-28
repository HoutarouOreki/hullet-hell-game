package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Pattern;

public class ActionArgBase {
    public final String name;
    public final String description;
    public final String example;
    public final Pattern pattern;
    public final boolean optional;

    public ActionArgBase(String name, String description, String example, Pattern pattern, boolean optional) {
        this.name = name;
        this.description = description;
        this.example = example;
        this.pattern = pattern;
        this.optional = optional;
    }
}
