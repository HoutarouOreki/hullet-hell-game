package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Matcher;

public interface ActionArgsParserCallback<T> {
    void run(T value);

    T parseMatcher(Matcher matcher);
}
