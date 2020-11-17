package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.scripts.ScriptedBody;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptedActionConfiguration {
    public final String type;
    public final List<String> arguments;
    public final double scriptedTime;

    public ScriptedActionConfiguration(String line) {
        Pattern pattern = Pattern.compile("\\t+(\\d+(?:\\.\\d*)?)\\t+(\\w+):\\t+(.*)");
        Matcher match = pattern.matcher(line.split(" // ")[0]);
        if (!match.matches()) {
            throw new Error("Could not find a match for an action: " + line);
        }
        type = match.group(2);
        scriptedTime = Double.parseDouble(match.group(1));
        arguments = Arrays.asList(match.group(3).split(", "));
    }
}