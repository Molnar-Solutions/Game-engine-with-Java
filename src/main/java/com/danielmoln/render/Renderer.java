package com.danielmoln.render;

import com.danielmoln.model.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer
{
    public void prepare()
    {
        /*
        * Clear the color from the previous frame
        * */
        GL11.glClearColor(1, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void render(RawModel model)
    {
        GL30.glBindVertexArray(model.getVaoID());
        /*
        *
        * Which element will we want to use ?
        * 0: vertex positions
        * 1: vertex colors
        * 2: normal vectors
        * 3: texture coords
        * ...
        *
        * */
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
}
