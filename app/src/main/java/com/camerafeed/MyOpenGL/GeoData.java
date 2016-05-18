package com.camerafeed.MyOpenGL;

import android.opengl.Matrix;
import android.util.Log;

import java.util.Random;

/**
 * Created by NoobsDeSroobs on 11-May-16.
 */
public class GeoData {
    public float[] mVertices;
    public short[] mIndices;

    private GeoData() {
    }

    static public GeoData halfpipe() {
        GeoData creator = new GeoData();
        creator.mVertices = createVertices1(44);
        creator.mIndices = createIndices1(44);
        return creator;
    }

    static public GeoData circle() {
        GeoData creator = new GeoData();
        creator.mVertices = createVertices2(32);
        creator.mIndices = createIndices2(32);
        return creator;
    }

    static public GeoData grid(int width, int height) {
        GeoData creator = new GeoData();
        creator.mVertices = createGridVertices(width + 1, height + 1);
        creator.mIndices = createGridIndices(width + 1, height + 1);
        return creator;
    }

    static public GeoData Box3D() {
        GeoData creator = new GeoData();
        creator.mVertices = createBoxVertices();
        creator.mIndices = createBoxIndices();
        return creator;
    }
    public void setColor(float[] color) {
        for (int i = 0; i < mVertices.length; i+=6) {
            mVertices[i+3] = color[0];
            mVertices[i+4] = color[1];
            mVertices[i+5] = color[2];
        }
    }

    private static short[] createBoxIndices() {
        short[] indices = {
                0, 1, 2,
                2, 1, 3,
                4, 0, 6,
                6, 0, 2,
                5, 1, 4,
                4, 1, 0,
                6, 2, 7,
                7, 2, 3,
                7, 3, 5,
                5, 3, 1,
                5, 4, 7,
                7, 4, 6};

        return indices;
    }

    private static float[] createBoxVertices() {
        Random rand = new Random();
        float[] vertices= {
            // in counterclockwise order:
            -0.5f, -0.5f, -0.5f, //BLB 0
            rand.nextFloat() % 255, rand.nextFloat() % 255, rand.nextFloat() % 255,
            -0.5f, 0.5f, -0.5f,  //BLF 1
            rand.nextFloat() % 255, rand.nextFloat() % 255, rand.nextFloat() % 255,
            0.5f, -0.5f, -0.5f,  //BRB 2
            rand.nextFloat() % 255, rand.nextFloat() % 255, rand.nextFloat() % 255,
            0.5f, 0.5f, -0.5f,   //BRF 3
            rand.nextFloat() % 255, rand.nextFloat() % 255, rand.nextFloat() % 255,
            -0.5f, -0.5f, 0.5f,  //TLB 4
            rand.nextFloat() % 255, rand.nextFloat() % 255, rand.nextFloat() % 255,
            -0.5f, 0.5f, 0.5f,   //TLF 5
            rand.nextFloat() % 255, rand.nextFloat() % 255, rand.nextFloat() % 255,
            0.5f, -0.5f, 0.5f,   //TRB 6
            rand.nextFloat() % 255, rand.nextFloat() % 255, rand.nextFloat() % 255,
            0.5f, 0.5f, 0.5f,    //TRF 7
            rand.nextFloat() % 255, rand.nextFloat() % 255, rand.nextFloat() % 255
            };
        return vertices;

    }

    static float[] createGridVertices(int width, int height) {
        int numComponents = 6;
        float[] vertices = new float[numComponents * width * height];


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                vertices[numComponents * (y * width + x) + 0] = x - (width / 2);
                vertices[numComponents * (y * width + x) + 1] = y - (height / 2);
                vertices[numComponents * (y * width + x) + 2] = 0.0f;
                vertices[numComponents * (y * width + x) + 3] = 0.2f;
                vertices[numComponents * (y * width + x) + 4] = 0.2f;
                vertices[numComponents * (y * width + x) + 5] = 0.2f;
            }
        }
        return vertices;
    }

    static short[] createGridIndices(int width, int height) {
        int size = (width - 2) * (height - 2) * 4;//Inner Square, 4 connections per point
        size += width * 3 * 2;//Top and bottom edge, 3 connections per point
        size += height * 3 * 2;//Left and right edge, 3 connections per point
        size -= 4 * 4;//Subtract the duplicated corners, remove the 2 duplicate connections for each corner AND the 2 void ones.
        short[] indices = new short[size];
        int ctr = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 1; x++) {
                int currVert = (y * width + x);
                indices[ctr] = (short) currVert;
                indices[ctr + 1] = (short) (currVert + 1);
                ctr += 2;
            }
        }


        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                int currVert = (y * width + x);
                indices[ctr] = (short) currVert;
                indices[ctr + 1] = (short) (currVert + width);
                ctr += 2;
            }
        }

        Log.e("Test", "" + (size - ctr));

        return indices;
    }

    static float[] createVertices1(int n) {
        int NUM_COMPONENTS = 6;
        float S = 0.75f;
        float X = 1f;
        float z0 = 1.3f;
        float z1 = 1.1f;
        float dx = 2 * X / n;
        float[] vertices = new float[NUM_COMPONENTS * (n + 1) * 2];
        for (int i = 0; i < (n + 1); i++) {
            int I0 = 2 * NUM_COMPONENTS * i;
            int I1 = 2 * NUM_COMPONENTS * i + NUM_COMPONENTS;
            float x = -X + dx * i;
            float y = -(float) Math.sqrt(1.0 - x * x);
            vertices[I0 + 0] = S * x;
            vertices[I0 + 1] = S * y;
            vertices[I0 + 2] = S * z0;
            vertices[I0 + 3] = x;
            vertices[I0 + 4] = y;
            vertices[I0 + 5] = 0;

            vertices[I1 + 0] = S * x;
            vertices[I1 + 1] = S * y;
            vertices[I1 + 2] = S * z1;
            vertices[I1 + 3] = x;
            vertices[I1 + 4] = y;
            vertices[I1 + 5] = 0;
        }
        return vertices;
    }

    static short[] createIndices1(int n) {
        short[] indices = new short[(n + 1) * 2];
        for (short i = 0; i < (n + 1) * 2; i++) {
            indices[i] = i;
        }
        return indices;
    }

    static float[] createVertices2(int n) {
        int NUM_COMPONENTS = 6;
        float[] vertices = new float[NUM_COMPONENTS * (n + 2)];
        final float S = 0.9f;
        final float Y = -0.0f;
        vertices[0] = 0;
        vertices[1] = Y;
        vertices[2] = 0;
        vertices[3] = 0;
        vertices[4] = -1;
        vertices[5] = 0;
        for (int i = 0; i <= n; i++) {
            int I = 6 + 6 * i;
            float a = (float) (0.75 * 2 * Math.PI * i / n);
            float x = (float) (S * Math.cos(a));
            float z = (float) (S * Math.sin(a));
            vertices[I + 0] = x;
            vertices[I + 1] = Y;
            vertices[I + 2] = z;
            vertices[I + 3] = 0;
            vertices[I + 4] = -1;
            vertices[I + 5] = 0;
        }
        return vertices;
    }

    static short[] createIndices2(int n) {
        short[] indices = new short[(n + 2)];
        for (short i = 0; i < (n + 2); i++) {
            indices[i] = i;
        }
        return indices;
    }

    public static GeoData square() {
        GeoData creator = new GeoData();
        creator.mVertices = createSquareVertices();
        creator.mIndices = createSquareIndices();
        return creator;
    }

    private static short[] createSquareIndices() {
        short[] indices = {
                0, 1, 3,
                3, 1, 2
        };
        return indices;
    }

    private static float[] createSquareVertices() {
        float[] vertices = {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0,

        };
        return vertices;
    }
}
