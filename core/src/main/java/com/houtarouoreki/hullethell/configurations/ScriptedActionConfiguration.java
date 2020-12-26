package com.houtarouoreki.hullethell.configurations;

import java.util.List;

public class ScriptedActionConfiguration implements Comparable<ScriptedActionConfiguration> {
    private final int id;
    public final String type;
    public final List<String> arguments;
    public final double scriptedTime;
    public final String line;

    public ScriptedActionConfiguration(int id, String type, List<String> arguments, double scriptedTime, String line) {
        this.id = id;
        this.type = type;
        this.arguments = arguments;
        this.scriptedTime = scriptedTime;
        this.line = line;
    }

    @Override
    public int compareTo(ScriptedActionConfiguration o) {
        double dt = scriptedTime - o.scriptedTime;
        if (dt == 0)
            return id - o.id;
        else
            return (int) Math.signum(dt);
    }
}
