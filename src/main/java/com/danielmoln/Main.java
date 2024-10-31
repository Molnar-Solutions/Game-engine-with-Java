package com.danielmoln;

import com.danielmoln.load.Loader;
import com.danielmoln.model.RawModel;
import com.danielmoln.model.TexturedModel;
import com.danielmoln.render.Renderer;
import com.danielmoln.shaders.StaticShader;
import com.danielmoln.texture.ModelTexture;
import com.danielmoln.window.DisplayManager;
import org.lwjgl.opengl.Display;

public class Main {
    // @ MD Solutions
    public static void main(String[] args)
    {
        DisplayManager.createDisplay();

        /* Initializing */
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        /* Entity stuffs */
        float[] vertices = {
                /* X   Y  Z */
                -0.5f, 0.5f, 0, // 0
                -0.5f, -0.5f, 0, // 1
                0.0f, -0.5f, 0, // 2
                0.0f, 0.5f, 0 // 3
        };
        int[] indices = {
                0, 1, 3,
                1, 2, 3
        };
        float[] textureCoords = {
          1,1f, // V0,
          1,0f, // V1
          0,0f,  // V2
          0,1f   // V3
        };
        RawModel model = loader.loadToVao(vertices, textureCoords, indices);
        ModelTexture modelTexture = new ModelTexture(loader.loadTexture("block.png"));

        TexturedModel texturedModel = new TexturedModel(model, modelTexture);

        while (!Display.isCloseRequested())
        {
            renderer.prepare();
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}