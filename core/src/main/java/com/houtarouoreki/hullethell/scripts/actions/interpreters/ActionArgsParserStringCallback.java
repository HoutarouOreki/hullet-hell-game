package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Matcher;

public interface ActionArgsParserStringCallback extends ActionArgsParserCallback<String> {
    @Override
    default String parseMatcher(Matcher matcher) {
        return matcher.group(1);
    }
}
