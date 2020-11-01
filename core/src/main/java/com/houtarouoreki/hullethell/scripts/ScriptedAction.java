package com.houtarouoreki.hullethell.scripts;

import java.util.Arrays;
import java.util.List;

public class ScriptedAction {
    public String type;
    public List<String> arguments;
    public double time;

    public ScriptedAction(String line) {
        String[] s = line.split(" // ")[0].split("\t+");
        time = Double.parseDouble(s[0]);
        type = s[1].replace(":", "");
        arguments = Arrays.asList(s[2].split(", ").clone());
    }
}
