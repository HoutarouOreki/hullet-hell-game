package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Pattern;

public class ActionStringArg extends ActionArg<String, ActionArgsParserStringSetter> {
    public ActionStringArg(String name, String description, String example, Pattern pattern, ActionArgsParserStringSetter callback, boolean optional) {
        super(name, description, example, pattern, callback, optional);
    }
}
