package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Matcher;

public interface ActionArgsParserFloatCallback extends ActionArgsParserCallback<Float> {
    @Override
    default Float parseMatcher(Matcher matcher) {
        return Float.parseFloat(matcher.group(1));
    }
}
