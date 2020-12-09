package com.houtarouoreki.hullethell.configurations;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptedBodyConfiguration {
    public final List<ScriptedActionConfiguration> actions = new ArrayList<ScriptedActionConfiguration>();
    public String type;
    public String name;
    public String configName;

    public ScriptedBodyConfiguration(String line) {
        Pattern pattern = Pattern.compile("(.*): \"((.*)/(.*))\"");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new Error("Could not find a match for scripted body line: " + line);
        }
        name = matcher.group(1);
        type = matcher.group(3);
        configName = matcher.group(4);
    }
}
