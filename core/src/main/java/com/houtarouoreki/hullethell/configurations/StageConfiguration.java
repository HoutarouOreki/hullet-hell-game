package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.files.FileHandle;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StageConfiguration {
    public final List<ScriptedSectionConfiguration> sections;

    public StageConfiguration(FileHandle file) {
        this(Arrays.asList(new String(file.readBytes()).split("\\r?\\n")));
    }

    public StageConfiguration(List<String> lines) {
        sections = new ArrayList<ScriptedSectionConfiguration>();

        ScriptedBodyConfiguration currentBody = null;
        ScriptedSectionConfiguration currentSection = null;
        for (String line : lines) {
            if (line.matches("! (.*)")) {
                currentSection = new ScriptedSectionConfiguration(line.replaceFirst("! ", ""));
                sections.add(currentSection);
            }
            else if (line.matches(".*: \".*/.*\"")) {
                currentBody = new ScriptedBodyConfiguration(line);
                if (currentSection == null) {
                    throw new NullPointerException("Current section was null");
                }
                currentSection.bodies.add(currentBody);
            } else if (line.startsWith("\t")) {
                if (currentBody == null) {
                    throw new NullPointerException("Current body was null");
                }
                currentBody.actions.add(new ScriptedActionConfiguration(line));
            }
        }
    }
}
