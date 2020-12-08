package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class MusicConfigurationLoader  extends AsynchronousAssetLoader<MusicConfiguration, MusicConfigurationLoader.MusicConfigurationParameter> {
    public MusicConfigurationLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    MusicConfiguration musicConfiguration;

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, MusicConfigurationLoader.MusicConfigurationParameter parameter) {
        musicConfiguration = null;
        musicConfiguration = new MusicConfiguration(file);
    }

    @Override
    public MusicConfiguration loadSync(AssetManager manager, String fileName, FileHandle file, MusicConfigurationLoader.MusicConfigurationParameter parameter) {
        musicConfiguration = null;
        musicConfiguration = new MusicConfiguration(file);

        return musicConfiguration;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, MusicConfigurationLoader.MusicConfigurationParameter parameter) {
        return new Array<AssetDescriptor>();
    }

    public static class MusicConfigurationParameter extends AssetLoaderParameters<MusicConfiguration> {

    }
}
