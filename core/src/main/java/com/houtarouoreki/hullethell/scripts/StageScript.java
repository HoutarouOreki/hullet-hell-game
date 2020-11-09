package com.houtarouoreki.hullethell.scripts;

import java.util.ArrayList;
import java.util.List;

public class StageScript {
    public List<ScriptedBody> entities = new ArrayList<ScriptedBody>();

    private void readLines(List<String> lines) {
        ScriptedBody currentEntity = null;
        for (String line : lines) {
            if (line.contains("=")) {
                currentEntity = new ScriptedBody();
            } else if (line.startsWith("\t") && currentEntity != null) {
                currentEntity.actions.add(ScriptedAction.createScriptedAction(line));
            }
        }
    }
}
