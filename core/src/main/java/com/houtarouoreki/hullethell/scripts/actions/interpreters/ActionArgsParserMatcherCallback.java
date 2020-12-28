package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Matcher;

public interface ActionArgsParserMatcherCallback extends ActionArgsParserCallback<Matcher> {
    @Override
    default Matcher parseMatcher(Matcher matcher) {
        return matcher;
    }
}
