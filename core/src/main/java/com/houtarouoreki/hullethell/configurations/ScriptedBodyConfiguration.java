package com.houtarouoreki.hullethell.configurations;

import java.util.ArrayList;
import java.util.List;

public class ScriptedBodyConfiguration {
    public final List<ScriptedActionConfiguration> actions = new ArrayList<>();
    public final String line;
    public final String type;
    public final String name;
    public final String path;
    public final String configName;
    public boolean hasPreviousSection;
    public boolean hasNextSection;

    public ScriptedBodyConfiguration(String line, String name, String path) {
        this.line = line;
        this.name = name;
        this.path = path;
        String[] temp = path.split("/");
        type = temp[0];
        configName = temp[1];
    }
}
