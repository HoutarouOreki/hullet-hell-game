package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import com.houtarouoreki.hullethell.numbers.Vector2;

import java.util.regex.Matcher;

public interface ActionArgsParserVector2Setter extends ActionArgsParserSetter<Vector2> {
    @Override
    default Vector2 parseMatcher(Matcher matcher) {
        String[] coordinates = matcher.group(1).split(", ");
        return new Vector2(Float.parseFloat(coordinates[0]), Float.parseFloat(coordinates[1]));
    }
}
