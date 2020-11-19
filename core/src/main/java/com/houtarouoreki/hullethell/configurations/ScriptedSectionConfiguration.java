package com.houtarouoreki.hullethell.configurations;

import java.util.ArrayList;
import java.util.List;

public class ScriptedSectionConfiguration {
    public final List<ScriptedBodyConfiguration> bodies = new ArrayList<ScriptedBodyConfiguration>();
    public final String name;

    public ScriptedSectionConfiguration(String name) {
        this.name = name;
    }
}
