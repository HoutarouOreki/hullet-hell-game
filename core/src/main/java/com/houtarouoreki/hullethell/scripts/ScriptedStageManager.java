package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
import com.houtarouoreki.hullethell.environment.World;

import java.util.*;

public class ScriptedStageManager {
    private final Queue<ScriptedSection> sections = new LinkedList<ScriptedSection>();
    private final AssetManager assetManager;
    private final World world;

    public ScriptedStageManager(World world, StageConfiguration script, AssetManager am) {
        this.world = world;
        assetManager = am;
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
}
