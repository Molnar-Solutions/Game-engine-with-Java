package com.danielmoln.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class ShaderProgram {
    private static final String BASE_URL = "src/main/resources/shaders/";

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    public ShaderProgram(String vertexFilePath, String fragmentFilePath)
    {
        vertexShaderID = loadShader(vertexFilePath, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFilePath, GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
    }

    public void start()
    {
        GL20.glUseProgram(programID);
    }

    public void stop()
    {
        GL20.glUseProgram(0);
    }

    public void cleanUp()
    {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteProgram(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();
    protected void bindAttribute(int attribute, String variableName)
    {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    private static int loadShader(String relativePath, int type)
    {
        StringBuilder shaderSource = new StringBuilder();
        /* File reading */
        try {
            BufferedReader br = new BufferedReader(new FileReader(BASE_URL + relativePath));
            String line;

            while ((line = br.readLine()) != null)
            {
                shaderSource.append(line + "\n");
            }

            br.close();
        } catch (IOException ex) {
            System.err.println("Couldn't read file!");
            ex.printStackTrace();
            System.exit(-1);
        }

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
        {
            System.err.println(GL20.glGetShaderInfoLog(shaderID, 500) + "");
            System.err.println("Couldn't compile shader.");
            System.exit(-1);
        }

        return shaderID;
    }
}
