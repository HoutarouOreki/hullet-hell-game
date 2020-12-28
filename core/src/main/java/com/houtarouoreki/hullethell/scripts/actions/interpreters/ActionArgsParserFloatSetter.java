package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Matcher;

public interface ActionArgsParserFloatSetter extends ActionArgsParserSetter<Float> {
    @Override
    default Float parseMatcher(Matcher matcher) {
        return Float.parseFloat(matcher.group(1));
    }
}
