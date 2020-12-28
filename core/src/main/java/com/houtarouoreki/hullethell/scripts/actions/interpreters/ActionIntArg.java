package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Pattern;

public class ActionIntArg extends ActionArg<Integer, ActionArgsParserIntCallback> {
    public ActionIntArg(String name, String description, String example, Pattern pattern, ActionArgsParserIntCallback callback, boolean optional) {
        super(name, description, example, pattern, callback, optional);
    }
}
