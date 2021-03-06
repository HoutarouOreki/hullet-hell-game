package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import com.houtarouoreki.hullethell.numbers.Vector2;

import java.util.regex.Pattern;

public class ActionVector2Arg extends ActionArg<Vector2, ActionArgsParserVector2Setter> {
    public ActionVector2Arg(String name, String description, String example, Pattern pattern, ActionArgsParserVector2Setter callback, boolean optional) {
        super(name, description, example, pattern, callback, optional);
    }
}
