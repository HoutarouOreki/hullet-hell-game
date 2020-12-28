package com.houtarouoreki.hullethell.scripts.actions.interpreters;

import com.badlogic.gdx.math.Interpolation;

import java.util.regex.Pattern;

public class ActionInterpolationArg extends ActionArg<Interpolation, ActionArgsParserInterpolationSetter> {
    public ActionInterpolationArg(String name, String description, String example, Pattern pattern, ActionArgsParserInterpolationSetter callback, boolean optional) {
        super(name, description, example, pattern, callback, optional);
    }
}
