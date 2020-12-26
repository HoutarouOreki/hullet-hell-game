package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StageConfiguration {
    public final List<ScriptedSectionConfiguration> sections = new ArrayList<>();
    public final String name;

    public StageConfiguration(FileHandle file) {
        this(file.nameWithoutExtension(),
                Arrays.asList(new String(file.readBytes()).split("\\r?\\n")));
    }

    public StageConfiguration(String name, List<String> lines) {
        this.name = name;

        generateBodyAndActions(lines);
    }

    private void generateBodyAndActions(List<String> lines) {
        StageConfigurationCreator creator = new StageConfigurationCreator(lines);
        sections.addAll(creator.sections);
    }
}
