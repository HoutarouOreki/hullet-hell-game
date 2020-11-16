package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class StageConfigurationLoader extends AsynchronousAssetLoader<StageConfiguration, StageConfigurationLoader.StageConfigurationParameter> {
    StageConfiguration stageConfiguration;

    public StageConfigurationLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, StageConfigurationParameter parameter) {
        stageConfiguration = null;
        stageConfiguration = new StageConfiguration(file);
    }

    @Override
    public StageConfiguration loadSync(AssetManager manager, String fileName, FileHandle file, StageConfigurationParameter parameter) {
        stageConfiguration = null;
        stageConfiguration = new StageConfiguration(file);

        return stageConfiguration;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, StageConfigurationParameter parameter) {
        return new Array<AssetDescriptor>();
    }

    public static class StageConfigurationParameter extends AssetLoaderParameters<StageConfiguration> {

    }
}