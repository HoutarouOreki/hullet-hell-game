package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class ShipConfigurationLoader extends AsynchronousAssetLoader<ShipConfiguration, ShipConfigurationLoader.ShipConfigurationParameter> {
    public ShipConfigurationLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    ShipConfiguration shipConfiguration;

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, ShipConfigurationParameter parameter) {
        shipConfiguration = null;
        shipConfiguration = new ShipConfiguration(file);
    }

    @Override
    public ShipConfiguration loadSync(AssetManager manager, String fileName, FileHandle file, ShipConfigurationParameter parameter) {
        shipConfiguration = null;
        shipConfiguration = new ShipConfiguration(file);

        return shipConfiguration;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ShipConfigurationParameter parameter) {
        return new Array<>();
    }

    public static class ShipConfigurationParameter extends AssetLoaderParameters<ShipConfiguration> {

    }
}
