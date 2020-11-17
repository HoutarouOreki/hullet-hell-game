package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.files.FileHandle;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StageConfiguration {
    public final List<ScriptedBodyConfiguration> bodies;

    public StageConfiguration(FileHandle file) {
        this(Arrays.asList(new String(file.readBytes()).split("\\r?\\n")));
    }

    public StageConfiguration(List<String> lines) {
        bodies = new ArrayList<ScriptedBodyConfiguration>();

        ScriptedBodyConfiguration currentBody = null;
        for (String line : lines) {
            if (line.matches(".*: \".*/.*\"")) {
                if (currentBody != null) {
                    bodies.add(currentBody);
                }
                currentBody = new ScriptedBodyConfiguration(line);
            } else if (line.startsWith("\t") && currentBody != null) {
                currentBody.actions.add(new ScriptedActionConfiguration(line));
            }
        }
        if (currentBody != null) {
            bodies.add(currentBody);
        }
    }
}
