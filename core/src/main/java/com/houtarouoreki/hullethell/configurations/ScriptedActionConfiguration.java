package com.houtarouoreki.hullethell.configurations;

import java.util.List;

public class ScriptedActionConfiguration implements Comparable<ScriptedActionConfiguration> {
    public final String type;
    public final List<String> arguments;
    public final double scriptedTime;
    public final String line;
    public final int scriptFileLineNumber;

    public ScriptedActionConfiguration(int lineNumber, String type, List<String> arguments, double scriptedTime, String line) {
        scriptFileLineNumber = lineNumber;
        this.type = type;
        this.arguments = arguments;
        this.scriptedTime = scriptedTime;
        this.line = line;
    }

    @Override
    public int compareTo(ScriptedActionConfiguration o) {
        double dt = scriptedTime - o.scriptedTime;
        if (dt == 0)
            return scriptFileLineNumber - o.scriptFileLineNumber;
        else
            return (int) Math.signum(dt);
    }
}
