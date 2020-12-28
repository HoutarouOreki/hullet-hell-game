package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionMatcherArg extends ActionArg<Matcher, ActionArgsParserMatcherSetter> {
    public ActionMatcherArg(String name, String description, String example, Pattern pattern, ActionArgsParserMatcherSetter callback, boolean optional) {
        super(name, description, example, pattern, callback, optional);
    }
}
