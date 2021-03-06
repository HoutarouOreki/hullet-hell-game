package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.helpers.ParsingHelpers;

import java.util.regex.Matcher;

public interface ActionArgsParserInterpolationSetter extends ActionArgsParserSetter<Interpolation> {
    @Override
    default Interpolation parseMatcher(Matcher matcher) {
        return ParsingHelpers.getInterpolationFromString(matcher.group(1));
    }
}
