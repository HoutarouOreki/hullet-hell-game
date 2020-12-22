package com.houtarouoreki.hullethell.configurations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScriptedSectionConfiguration {
    public final HashMap<String, Integer> flagsRequiredToStart = new HashMap<>();
    public final List<ScriptedBodyConfiguration> bodies = new ArrayList<>();
    public final List<ScriptedActionConfiguration> actions = new ArrayList<>();
    public final String name;
    public final String type;
    public final String whileParameters;

    public ScriptedSectionConfiguration(String type, String name, String whileParameters) {
        this.type = type;
        this.name = name;
        this.whileParameters = whileParameters;
    }
}
