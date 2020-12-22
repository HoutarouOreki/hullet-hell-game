package com.houtarouoreki.hullethell.configurations;

import java.util.List;

public class ScriptedActionConfiguration {
    public final String type;
    public final List<String> arguments;
    public final double scriptedTime;
    public final String line;

    public ScriptedActionConfiguration(String type, List<String> arguments, double scriptedTime, String line) {
        this.type = type;
        this.arguments = arguments;
        this.scriptedTime = scriptedTime;
        this.line = line;
    }
}
