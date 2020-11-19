package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
import com.houtarouoreki.hullethell.environment.World;

import java.util.LinkedList;
import java.util.Queue;

public class ScriptedStageManager {
    private final Queue<ScriptedSection> sections = new LinkedList<ScriptedSection>();

    public ScriptedStageManager(World world, StageConfiguration script, AssetManager am) {
        for (ScriptedSectionConfiguration sectionConfiguration : script.sections) {
            sections.add(new ScriptedSection(am, world, sectionConfiguration));
        }
    }

    public void update(double delta) {
        while (sections.size() > 0 && sections.peek().isFinished()) {
            sections.remove();
        }
        if (sections.size() > 0) {
            sections.peek().update(delta);
        }
    }

    public String getCurrentStageName() {
        return sections.size() > 0 ? sections.peek().getName() : "null";
    }

    public int getWaitingBodiesCount() {
        return sections.size() > 0 ? sections.peek().getWaitingBodiesCount() : 0;
    }

    public int getActiveBodiesCount() {
        return sections.size() > 0 ? sections.peek().getActiveBodiesCount() : 0;
    }
}
