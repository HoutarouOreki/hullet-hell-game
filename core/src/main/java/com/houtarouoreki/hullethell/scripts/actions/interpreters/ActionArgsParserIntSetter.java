package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Matcher;

public interface ActionArgsParserIntSetter extends ActionArgsParserSetter<Integer> {
    @Override
    default Integer parseMatcher(Matcher matcher) {
        return Integer.parseInt(matcher.group(1));
    }
}