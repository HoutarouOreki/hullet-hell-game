package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
import com.houtarouoreki.hullethell.environment.World;

import java.util.LinkedList;
import java.util.Queue;

public class ScriptedStageManager {
    private final Queue<ScriptedSection> sections = new LinkedList<ScriptedSection>();
    private int allBodiesAmount;
    private int bodiesRemovedPrevSections;

    public ScriptedStageManager(World world, StageConfiguration script) {
        for (ScriptedSectionConfiguration sectionConfiguration : script.sections) {
            ScriptedSection section = new ScriptedSection(world, sectionConfiguration);
            sections.add(section);
            allBodiesAmount += section.getAllBodiesAmount();
        }
    }

    public void update(double delta) {
        while (sections.size() > 0 && sections.peek().isFinished()) {
            assert sections.peek() != null;
            bodiesRemovedPrevSections += sections.peek().getBodiesRemovedAmount();
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

    public int getBodiesCount() {
        return sections.size() > 0 ? sections.peek().getBodiesCount() : 0;
    }

    public int getBodiesRemovedAmount() {
        return bodiesRemovedPrevSections +
                (sections.peek() != null ? sections.peek().getBodiesRemovedAmount() : 0);
    }

    public int getSectionWaitingActions() {
        return sections.size() > 0 ? sections.peek().getWaitingActionsCount() : 0;
    }

    public int getSectionCurrentActions() {
        return sections.size() > 0 ? sections.peek().getCurrentActionsCount() : 0;
    }

    public float getProgression() {
        return getBodiesRemovedAmount() / (float) allBodiesAmount;
    }

    public boolean isFinished() {
        return sections.size() == 0;
    }
}
