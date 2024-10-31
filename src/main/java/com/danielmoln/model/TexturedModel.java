package com.danielmoln.model;

import com.danielmoln.texture.ModelTexture;

public class TexturedModel
{
    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel raw, ModelTexture modelTexture)
    {
        this.rawModel = raw;
        this.texture = modelTexture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
