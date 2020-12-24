package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class BodyConfigurationLoader extends AsynchronousAssetLoader<BodyConfiguration, BodyConfigurationLoader.BodyConfigurationParameter> {
    public BodyConfigurationLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    BodyConfiguration bodyConfiguration;

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, BodyConfigurationParameter parameter) {
        bodyConfiguration = null;
        bodyConfiguration = new BodyConfiguration(file);
    }

    @Override
    public BodyConfiguration loadSync(AssetManager manager, String fileName, FileHandle file, BodyConfigurationParameter parameter) {
        bodyConfiguration = null;
        bodyConfiguration = new BodyConfiguration(file);

        return bodyConfiguration;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, BodyConfigurationParameter parameter) {
        return new Array<>();
    }

    public static class BodyConfigurationParameter extends AssetLoaderParameters<BodyConfiguration> {

    }
}
