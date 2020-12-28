package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Pattern;

public class ActionFloatArg extends ActionArg<Float, ActionArgsParserFloatSetter> {
    public ActionFloatArg(String name, String description, String example, Pattern pattern, ActionArgsParserFloatSetter callback, boolean optional) {
        super(name, description, example, pattern, callback, optional);
    }
}
