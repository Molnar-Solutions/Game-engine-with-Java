package com.danielmoln.load;

import com.danielmoln.model.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

// @ deal with loading 3d models into memory
// @ by storing positional data about a model in a VAO
public class Loader
{
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();

    public RawModel loadToVao(float[] positions)
    {
        int vaoID = createVAO();
        storeDataInAttributeList(0, positions);
        unbindVAO();
        return new RawModel(vaoID, (positions.length / 3));
    }

    private int createVAO()
    {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        /*
        *
        * @ If you want to do something with a vao then you have to bind it.
        *
        * */
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, float[] data)
    {
        // Create an empty vbo
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        /*
        *
        *
        * @ It is same than the vao
        * */
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        // What do we use for data ? Will it change or it will change ?
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO()
    {
        /*
         *
         * @ VAO stay bounded until we 'disable' it.
         * @ Unbind the currently active vao.
         *
         * */
        GL30.glBindVertexArray(0);
    }

    public void cleanUp()
    {
        vaos.forEach(vao -> {
            GL30.glDeleteVertexArrays(vao);
        });
        vbos.forEach(vbo -> {
            GL30.glDeleteVertexArrays(vbo);
        });
    }

    // Convert incoming data to an accepted form.
    private FloatBuffer storeDataInFloatBuffer(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip(); // finish writing
        return buffer;
    }
}
