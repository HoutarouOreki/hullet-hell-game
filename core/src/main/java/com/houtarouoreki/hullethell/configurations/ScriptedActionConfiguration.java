package com.houtarouoreki.hullethell.configurations;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptedActionConfiguration {
    public final String type;
    public final List<String> arguments;
    public final double scriptedTime;

    public ScriptedActionConfiguration(String line) {
        Pattern pattern = Pattern.compile("^\\t*(\\d+(?:\\.\\d*)?)\\t+(\\w+):?\\t+(.*)");
        Matcher match = pattern.matcher(line.split(" // ")[0]);
        if (match.matches()) {
            type = match.group(2);
            scriptedTime = Double.parseDouble(match.group(1));
            arguments = Arrays.asList(match.group(3).split(", "));
        }
        else {
            pattern = Pattern.compile("^(\\d*\\.?\\d*)(?:\\t*(.*))?$");
            match = pattern.matcher(line.split(" // ")[0]);
            if (match.matches()) {
                String text = match.groupCount() == 2 ? match.group(2) : "h";
                type = "Generated Dialogue";
                arguments = Arrays.asList(text, match.group(1));
                scriptedTime = 0;
            } else {
                throw new Error("Could not find a match for an action: " + line);
            }
        }
    }
}
