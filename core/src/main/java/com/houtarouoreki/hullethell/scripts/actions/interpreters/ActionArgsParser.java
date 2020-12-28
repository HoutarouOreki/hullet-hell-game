package com.houtarouoreki.hullethell.scripts.actions.interpreters;


import com.houtarouoreki.hullethell.scripts.actions.interpreters.exceptions.RequiredArgumentNotFoundException;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.exceptions.UninterpretedArgumentStringException;

import java.util.*;
import java.util.regex.Matcher;

public class ActionArgsParser {
    public final List<ActionIntArg> intArgs = new ArrayList<>();
    public final List<ActionFloatArg> floatArgs = new ArrayList<>();
    public final List<ActionStringArg> stringArgs = new ArrayList<>();
    public final List<ActionVector2Arg> vector2Args = new ArrayList<>();
    public final List<ActionMatcherArg> matcherArgs = new ArrayList<>();
    public final List<ActionInterpolationArg> interpolationArgs = new ArrayList<>();
    private final HashMap<ActionArgBase, Boolean> requiredArgsProvided = new HashMap<>();

    public ActionArgsParser() {
        registerRequiredArgs(intArgs);
        registerRequiredArgs(floatArgs);
        registerRequiredArgs(stringArgs);
        registerRequiredArgs(vector2Args);
        registerRequiredArgs(matcherArgs);
        registerRequiredArgs(interpolationArgs);
    }

    private void registerRequiredArgs(List<? extends ActionArgBase> args) {
        for (ActionArgBase arg : args) {
            if (!arg.optional)
                requiredArgsProvided.put(arg, false);
        }
    }


    public final void run(Iterable<String> arguments) throws RequiredArgumentNotFoundException, UninterpretedArgumentStringException {
        for (String arg : arguments)
            interpretArgument(arg);
        validateRequiredArgsWereProvided();
    }

    private void validateRequiredArgsWereProvided() throws RequiredArgumentNotFoundException {
        for (Map.Entry<ActionArgBase, Boolean> argProvided : requiredArgsProvided.entrySet()) {
            if (!argProvided.getValue())
                throw new RequiredArgumentNotFoundException(argProvided.getKey());
        }
    }

    private void interpretArgument(String arg) throws IllegalArgumentException, UninterpretedArgumentStringException {
        if (callMatchingCallback(arg, intArgs)) return;
        if (callMatchingCallback(arg, floatArgs)) return;
        if (callMatchingCallback(arg, stringArgs)) return;
        if (callMatchingCallback(arg, vector2Args)) return;
        if (callMatchingCallback(arg, matcherArgs)) return;
        if (callMatchingCallback(arg, interpolationArgs)) return;
        throw new UninterpretedArgumentStringException(arg);
    }

    private <E, S extends ActionArgsParserSetter<E>, T extends ActionArg<E, S>> boolean callMatchingCallback(String s, List<T> args) {
        Matcher matcher;
        for (T arg : args) {
            if ((matcher = arg.pattern.matcher(s)).matches()) {
                arg.callback.set(arg.callback.parseMatcher(matcher));
                return true;
            }
        }
        return false;
    }
}