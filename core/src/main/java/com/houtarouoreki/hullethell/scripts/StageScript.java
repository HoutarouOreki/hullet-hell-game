package com.houtarouoreki.hullethell.scripts;

import java.util.ArrayList;
import java.util.List;

public class StageScript {
    public List<ScriptedEntity> entities = new ArrayList<ScriptedEntity>();

    private void readLines(List<String> lines) {
        ScriptedEntity currentEntity;
        for (String line : lines) {
            if (line.contains("=")) {
                currentEntity = new ScriptedEntity();
            } else if (line.startsWith("\t")) {
                ScriptedAction action = new ScriptedAction(line);
            }
        }
    }
}
