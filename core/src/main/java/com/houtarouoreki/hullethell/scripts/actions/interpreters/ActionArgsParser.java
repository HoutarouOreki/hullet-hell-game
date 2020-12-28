package com.houtarouoreki.hullethell.scripts.actions.interpreters;


import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ActionArgsParser {
    public final List<ActionIntArg> intArgs = new ArrayList<>();
    public final List<ActionFloatArg> floatArgs = new ArrayList<>();
    public final List<ActionStringArg> stringArgs = new ArrayList<>();
    public final List<ActionVector2Arg> vector2Args = new ArrayList<>();
    public final List<ActionMatcherArg> matcherArgs = new ArrayList<>();
    public final List<ActionInterpolationArg> interpolationArgs = new ArrayList<>();

    public final void run(Iterable<String> arguments) {
        for (String arg : arguments)
            interpretArgument(arg);
    }

    private void interpretArgument(String arg) {
        if (callMatchingCallback(arg, intArgs)) return;
        if (callMatchingCallback(arg, floatArgs)) return;
        if (callMatchingCallback(arg, stringArgs)) return;
        if (callMatchingCallback(arg, vector2Args)) return;
        if (callMatchingCallback(arg, matcherArgs)) return;
        callMatchingCallback(arg, interpolationArgs);
    }

    private <E, S extends ActionArgsParserCallback<E>, T extends ActionArg<E, S>> boolean callMatchingCallback(String s, List<T> args) {
        Matcher matcher;
        for (T arg : args) {
            if ((matcher = arg.pattern.matcher(s)).matches()) {
                arg.callback.run(arg.callback.parseMatcher(matcher));
                return true;
            }
        }
        return false;
    }
}