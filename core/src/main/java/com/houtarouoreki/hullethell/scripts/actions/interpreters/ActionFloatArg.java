package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Pattern;

public class ActionFloatArg extends ActionArg<Float, ActionArgsParserFloatCallback> {
    public ActionFloatArg(String name, String description, String example, Pattern pattern, ActionArgsParserFloatCallback callback, boolean optional) {
        super(name, description, example, pattern, callback, optional);
    }
}
