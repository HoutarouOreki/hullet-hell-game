package com.houtarouoreki.hullethell.configurations;

import java.util.ArrayList;
import java.util.List;

public class ScriptedBodyConfiguration implements Comparable<ScriptedBodyConfiguration> {
    public final List<ScriptedActionConfiguration> actions = new ArrayList<>();
    private final int id;
    public final String line;
    public final String type;
    public final String name;
    public final String path;
    public final String configName;
    public boolean hasPreviousSection;
    public boolean hasNextSection;

    public ScriptedBodyConfiguration(int id, String line, String name, String path) {
        this.id = id;
        this.line = line;
        this.name = name;
        this.path = path;
        String[] temp = path.split("/");
        type = temp[0];
        configName = temp.length > 1 ? temp[1] : null;
    }

    @Override
    public int compareTo(ScriptedBodyConfiguration o) {
        double dt = actions.get(0).scriptedTime - o.actions.get(0).scriptedTime;
        if (dt == 0)
            return id - o.id;
        return (int) Math.signum(dt);
    }
}
