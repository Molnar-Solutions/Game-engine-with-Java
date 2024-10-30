package com.danielmoln;

import com.danielmoln.load.Loader;
import com.danielmoln.model.RawModel;
import com.danielmoln.render.Renderer;
import com.danielmoln.window.DisplayManager;
import org.lwjgl.opengl.Display;

public class Main {
    // @ MD Solutions
    public static void main(String[] args)
    {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0f, 0.5f, 0f
        };
        RawModel model = loader.loadToVao(vertices);

        while (!Display.isCloseRequested())
        {
            renderer.prepare();
            // game logic
            renderer.render(model);
            DisplayManager.updateDisplay();
        }

        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}