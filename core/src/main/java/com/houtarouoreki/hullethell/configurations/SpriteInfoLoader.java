package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.graphics.SpriteInfo;

public class SpriteInfoLoader extends AsynchronousAssetLoader<SpriteInfo, SpriteInfoLoader.SpriteInfoParameter> {
    SpriteInfo spriteInfo;

    public SpriteInfoLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SpriteInfoParameter parameter) {
        spriteInfo = null;
        SpriteInfo spriteInfo = new SpriteInfo(file);
        fillInData(file, spriteInfo);
        this.spriteInfo = spriteInfo;
        addTexturesToLoad();
    }

    @Override
    public SpriteInfo loadSync(AssetManager manager, String fileName, FileHandle file, SpriteInfoParameter parameter) {
        spriteInfo = null;
        spriteInfo = new SpriteInfo(file);
        fillInData(file, spriteInfo);
        addTexturesToLoad();
        return spriteInfo;
    }

    private void addTexturesToLoad() {
        for (SpriteInfo.Size size : spriteInfo.availableSizes) {
            if (spriteInfo.framesCount.get(size) == 0) {
                for (int i = 0; i <= spriteInfo.damageTexturesCount.get(size); i++)
                    HulletHellGame.getAssetManager().load(spriteInfo.getDamagedPath(size, i), Texture.class);
            } else {
                for (int i = 0; i < spriteInfo.framesCount.get(size); i++)
                    HulletHellGame.getAssetManager().load(spriteInfo.getPath(size, i), Texture.class);
            }
        }
    }

    private void fillInData(FileHandle file, SpriteInfo spriteInfo) {
        String path = file.path();
        int lastFSlashIndex = path.lastIndexOf('/');
        String directory = path.substring(0, lastFSlashIndex);
        int lastDotIndex = path.lastIndexOf('.');
        if (spriteInfo.textureFolder == null) {
            spriteInfo.textureFolder = directory;
        }
        if (spriteInfo.textureName == null) {
            spriteInfo.textureName = path
                    .substring(lastFSlashIndex + 1, lastDotIndex)
                    .replace("-sprite", "");
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, SpriteInfoParameter parameter) {
        return new Array<>();
    }

    public static class SpriteInfoParameter extends AssetLoaderParameters<SpriteInfo> {

    }
}
