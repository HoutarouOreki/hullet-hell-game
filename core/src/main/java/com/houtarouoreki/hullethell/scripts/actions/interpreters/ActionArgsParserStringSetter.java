package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Matcher;

public interface ActionArgsParserStringSetter extends ActionArgsParserSetter<String> {
    @Override
    default String parseMatcher(Matcher matcher) {
        return matcher.group(1);
    }
}
