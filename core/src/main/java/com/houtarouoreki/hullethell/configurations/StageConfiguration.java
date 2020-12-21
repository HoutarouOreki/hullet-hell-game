package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StageConfiguration {
    public final List<ScriptedSectionConfiguration> sections;
    public final String name;

    public StageConfiguration(FileHandle file) {
        this(file.nameWithoutExtension(),
                Arrays.asList(new String(file.readBytes()).split("\\r?\\n")));
    }

    public StageConfiguration(String name, List<String> lines) {
        this.name = name;
        sections = new ArrayList<ScriptedSectionConfiguration>();

        final HashMap<String, ScriptedBodyConfiguration> allBodies
                = new HashMap<String, ScriptedBodyConfiguration>();

        ScriptedBodyConfiguration currentBody = null;
        ScriptedSectionConfiguration currentSection = null;
        boolean isDialogueSection = false;
        int i = 1;
        for (String line : lines) {
            if (line.matches("(?:^!$|^! (.*)?)")) {
                currentSection = new ScriptedSectionConfiguration(line.
                        replaceFirst("! ?", ""));
                isDialogueSection = false;
                sections.add(currentSection);
            } else if (line.matches("!while (.*)")) {
                currentSection = new ScriptedSectionConfiguration(line
                        .replaceFirst("!while ", ""));
                currentSection.type = "while";
                isDialogueSection = false;
                sections.add(currentSection);
            } else if (line.matches("!dialogue ?(.*)?")) {
                currentSection = new ScriptedSectionConfiguration(line
                        .replaceFirst("!dialogue ?", ""));
                currentSection.type = "dialogue";
                isDialogueSection = true;
                sections.add(currentSection);
            } else if (isDialogueSection && line.matches("^.*:$")) {
            } else if (isDialogueSection && line.matches("(?:\\d+(?:\\.\\d+)?)?\\t+.*$")) {
                currentSection.actions.add(new ScriptedActionConfiguration(line));
            } else if (isDialogueSection && line.matches("^\\d+(?:\\.\\d+)?")) {
                currentSection.actions.add(new ScriptedActionConfiguration(line));
            } else if (line.matches("^\\d+(?:\\.\\d+)?\\t+.*\\t+.*")) {
                if (currentSection == null)
                    throw new NullPointerException("Current section was null");
                currentSection.actions.add(new ScriptedActionConfiguration(line));
            } else if (line.matches("^\\w*:(?: \".*\\/.*\")?")) {
                String bodyName = line.substring(0, line.indexOf(':'));
                if (allBodies.containsKey(bodyName))
                    currentBody = allBodies.get(bodyName);
                else {
                    currentBody = new ScriptedBodyConfiguration(line);
                    allBodies.put(bodyName, currentBody);
                }
                if (currentSection == null)
                    throw new NullPointerException("Current section was null\n"
                            + name + ", line " + i);
                currentSection.bodies.add(currentBody);
            } else if (line.startsWith("\t")) {
                if (currentBody == null) {
                    throw new NullPointerException("Current body was null\n"
                            + name + ", line " + i);
                }
                currentBody.actions.add(new ScriptedActionConfiguration(line));
            }
            i++;
        }
    }
}
