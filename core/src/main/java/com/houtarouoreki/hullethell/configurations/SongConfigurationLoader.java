package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class SongConfigurationLoader extends AsynchronousAssetLoader<SongConfiguration, SongConfigurationLoader.SongConfigurationParameter> {
    public SongConfigurationLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    SongConfiguration songConfiguration;

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SongConfigurationParameter parameter) {
        songConfiguration = null;
        songConfiguration = new SongConfiguration(file);
    }

    @Override
    public SongConfiguration loadSync(AssetManager manager, String fileName, FileHandle file, SongConfigurationParameter parameter) {
        songConfiguration = null;
        songConfiguration = new SongConfiguration(file);

        return songConfiguration;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, SongConfigurationParameter parameter) {
        return new Array<AssetDescriptor>();
    }

    public static class SongConfigurationParameter extends AssetLoaderParameters<SongConfiguration> {

    }
}
