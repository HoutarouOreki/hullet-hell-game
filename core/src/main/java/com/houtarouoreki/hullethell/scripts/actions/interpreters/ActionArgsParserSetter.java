package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import java.util.regex.Matcher;

public interface ActionArgsParserSetter<T> {
    void set(T value);

    T parseMatcher(Matcher matcher);
}
