package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import org.mini2Dx.core.font.BitmapFont;
import org.mini2Dx.core.font.GameFont;

import java.util.Hashtable;

public final class Fonts {
    private static final Hashtable<String, FreeTypeFontGenerator> generators
            = new Hashtable<String, FreeTypeFontGenerator>();

    public static GameFont defaultFont;

    private Fonts() {
    }

    public static BitmapFont getFont(String fileName,
                                     FreeTypeFontGenerator.FreeTypeFontParameter parameters) {
        FreeTypeFontGenerator g;
        if (generators.containsKey(fileName))
            g = generators.get(fileName);
        else {
            g = new FreeTypeFontGenerator(Gdx.files.internal(fileName));
            generators.put(fileName, g);
        }
        parameters.flip = false;
        com.badlogic.gdx.graphics.g2d.BitmapFont temp = g.generateFont(parameters);
        return new BitmapFont(temp.getData(), temp.getRegion(), temp.usesIntegerPositions());
    }
}
