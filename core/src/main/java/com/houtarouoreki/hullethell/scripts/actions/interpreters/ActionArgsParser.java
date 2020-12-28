package com.houtarouoreki.hullethell.scripts.actions.interpreters;


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionArgsParser {
    public final HashMap<Pattern, ActionArgsParserIntCallback> intCallbacks = new HashMap<>();
    public final HashMap<Pattern, ActionArgsParserFloatCallback> floatCallbacks = new HashMap<>();
    public final HashMap<Pattern, ActionArgsParserStringCallback> stringCallbacks = new HashMap<>();
    public final HashMap<Pattern, ActionArgsParserVector2Callback> vector2Callbacks = new HashMap<>();
    public final HashMap<Pattern, ActionArgsParserMatcherCallback> matcherCallbacks = new HashMap<>();
    public final HashMap<Pattern, ActionArgsParserInterpolationCallback> interpolationCallbacks = new HashMap<>();

    public final void run(Iterable<String> arguments) {
        for (String arg : arguments)
            interpretArgument(arg);
    }

    private void interpretArgument(String arg) {
        if (callMatchingCallbacks(arg, intCallbacks)) return;
        if (callMatchingCallbacks(arg, floatCallbacks)) return;
        if (callMatchingCallbacks(arg, stringCallbacks)) return;
        if (callMatchingCallbacks(arg, vector2Callbacks)) return;
        if (callMatchingCallbacks(arg, matcherCallbacks)) return;
        callMatchingCallbacks(arg, interpolationCallbacks);
    }

    private <E, T extends ActionArgsParserCallback<E>> boolean callMatchingCallbacks(String arg, HashMap<Pattern, T> callbacks) {
        Matcher matcher;
        for (Pattern pattern : callbacks.keySet()) {
            if ((matcher = pattern.matcher(arg)).matches()) {
                ActionArgsParserCallback<E> callback = callbacks.get(pattern);
                callback.run(callback.parseMatcher(matcher));
                return true;
            }
        }
        return false;
    }
}