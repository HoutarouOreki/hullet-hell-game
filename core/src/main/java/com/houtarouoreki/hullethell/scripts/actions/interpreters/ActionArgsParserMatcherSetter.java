package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Matcher;

public interface ActionArgsParserMatcherSetter extends ActionArgsParserSetter<Matcher> {
    @Override
    default Matcher parseMatcher(Matcher matcher) {
        return matcher;
    }
}
