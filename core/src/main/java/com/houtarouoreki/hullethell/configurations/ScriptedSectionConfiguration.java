package com.houtarouoreki.hullethell.configurations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScriptedSectionConfiguration {
    public final HashMap<String, Integer> flagsRequiredToStart = new HashMap<String, Integer>();
    public final List<ScriptedBodyConfiguration> bodies = new ArrayList<ScriptedBodyConfiguration>();
    public final List<ScriptedActionConfiguration> actions = new ArrayList<ScriptedActionConfiguration>();
    public final String name;
    public String type = "";

    public ScriptedSectionConfiguration(String name) {
        this.name = name;
    }
}
