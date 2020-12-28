package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Pattern;

public class ActionStringArg extends ActionArg<String, ActionArgsParserStringCallback> {
    public ActionStringArg(String name, String description, String example, Pattern pattern, ActionArgsParserStringCallback callback, boolean optional) {
        super(name, description, example, pattern, callback, optional);
    }
}
