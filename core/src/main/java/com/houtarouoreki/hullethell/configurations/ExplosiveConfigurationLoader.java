package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class ExplosiveConfigurationLoader extends AsynchronousAssetLoader<ExplosiveConfiguration, ExplosiveConfigurationLoader.ExplosiveConfigurationParameter> {
    public ExplosiveConfigurationLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    ExplosiveConfiguration explosiveConfiguration;

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, ExplosiveConfigurationParameter parameter) {
        explosiveConfiguration = null;
        explosiveConfiguration = new ExplosiveConfiguration(file);
    }

    @Override
    public ExplosiveConfiguration loadSync(AssetManager manager, String fileName, FileHandle file, ExplosiveConfigurationParameter parameter) {
        explosiveConfiguration = null;
        explosiveConfiguration = new ExplosiveConfiguration(file);

        return explosiveConfiguration;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ExplosiveConfigurationParameter parameter) {
        return new Array<>();
    }

    public static class ExplosiveConfigurationParameter extends AssetLoaderParameters<ExplosiveConfiguration> {

    }
}
