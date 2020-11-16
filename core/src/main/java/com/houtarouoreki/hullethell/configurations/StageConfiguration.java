package com.houtarouoreki.hullethell.configurations;

import box2dLight.Light;
import com.badlogic.gdx.files.FileHandle;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.ScriptedBody;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StageConfiguration {
    public final List<ScriptedBody> bodies;

    public StageConfiguration(FileHandle file) {
        this(Arrays.asList(new String(file.readBytes()).split("\\r?\\n")));
    }

    public StageConfiguration(List<String> lines) {
        bodies = new ArrayList<ScriptedBody>();

        ScriptedBody currentEntity = null;
        for (String line : lines) {
            if (line.matches(".*: \".*/.*\"")) {
                if (currentEntity != null) {
                    bodies.add(currentEntity);
                }
                currentEntity = new ScriptedBody(line);
            } else if (line.startsWith("\t") && currentEntity != null) {
                currentEntity.actions.add(ScriptedAction.createScriptedAction(line));
            }
        }
        if (currentEntity != null) {
            bodies.add(currentEntity);
        }
    }
}
