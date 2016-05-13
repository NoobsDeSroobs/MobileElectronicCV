package com.camerafeed.MyOpenGL;

import android.opengl.GLES20;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by NoobsDeSroobs on 11-May-16.
 */
public class VBO {
    int mNumIndices;

    int mIndexBufferId;
    int mVertexBufferId;
    boolean mUseNormals;
    boolean mUseTexCoords;

    int mType;
    int mNumComponents;
    int mStride;

    VBO(float[] vertices,               // array of vertex data
        short[] indices,            // indices
        int type,                   // GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES,
        // GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, and GL_TRIANGLES
        boolean vertexNormals,      // normals used ?
        boolean vertexTexCoords,    // texCoords used ?
        int stride) {               // struct size in bytes; if stride <= 0 -> stride will be calculated


        mType = type;
        mUseNormals = vertexNormals;
        mUseTexCoords = vertexTexCoords;

        mNumComponents = 3+3;
        if (mUseNormals) {
            mNumComponents += 3;
        }
        if (mUseTexCoords) {
            mNumComponents += 2;
        }

        if (stride <= 0) {
            mStride = 4 * mNumComponents;
        } else {
            mStride = stride;
        }

        int[] buffers = {0,0};
        GLES20.glGenBuffers(2, buffers, 0);

        mVertexBufferId = buffers[0];
        mIndexBufferId = buffers[1];

        createVertexBuffer(GLES20.GL_ARRAY_BUFFER, vertices, mVertexBufferId);
        createIndexBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indices, mIndexBufferId);
        mNumIndices = indices.length;
    }

    void deleteBuffers() {
        int[] buffers = {mVertexBufferId, mIndexBufferId};
        GLES20.glDeleteBuffers(2, buffers, 0);
        mVertexBufferId = 0;
        mIndexBufferId = 0;
    }

    void draw() {
        if (0 == mVertexBufferId) {
            return;
        }
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertexBufferId);

        GLES20.glEnableVertexAttribArray(Shader.VERTEX_POS);
        if (mUseNormals) {
            GLES20.glEnableVertexAttribArray(Shader.NORMAL_POS);
        }
        if (mUseTexCoords) {
            GLES20.glEnableVertexAttribArray(Shader.TEX_POS);
        }

        GLES20.glEnableVertexAttribArray(Shader.COLOR_POS);


        int offset = 0;

        GLES20.glVertexAttribPointer(
                Shader.VERTEX_POS,      // generic id
                3,                      // vertex has 3 components
                GLES20.GL_FLOAT,        // data type
                false,                  // no normalizing
                mStride,                // stride: sizeof(float) * number of components
                offset);                // offset 0; vertex starts at zero
        offset += 4 * 3;

            GLES20.glVertexAttribPointer(
                    Shader.COLOR_POS,
                    3,
                    GLES20.GL_FLOAT,
                    false,
                    mStride,
                    offset);
            offset += 4 * 3;

        if (mUseNormals) {

            GLES20.glVertexAttribPointer(
                    Shader.NORMAL_POS,
                    3,
                    GLES20.GL_FLOAT,
                    false,
                    mStride,
                    offset);
            offset += 4 * 3;
        }

        if (mUseTexCoords) {

            GLES20.glVertexAttribPointer(
                    Shader.TEX_POS,
                    2,                      // texCoord has 2 components
                    GLES20.GL_FLOAT,
                    false,
                    mStride,
                    offset);
            offset += 4 * 3;
        }

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferId);
        GLES20.glDrawElements(mType, mNumIndices, GLES20.GL_UNSIGNED_SHORT, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

        GLES20.glDisableVertexAttribArray(Shader.VERTEX_POS);
        GLES20.glDisableVertexAttribArray(Shader.NORMAL_POS);
        GLES20.glDisableVertexAttribArray(Shader.TEX_POS);
        GLES20.glDisableVertexAttribArray(Shader.COLOR_POS);

    }
    static void createVertexBuffer(int target, float[] vertices, int bufferId) {
        int size = vertices.length * 4;
        FloatBuffer fb = ByteBuffer.allocateDirect(4*vertices.length).order(ByteOrder.nativeOrder()).asFloatBuffer();
        fb.put(vertices);
        fb.position(0);

        createBuffer(target, fb, size, bufferId);
    }
    static void createIndexBuffer(int target, short[] indices, int bufferId) {
        int size = indices.length * 2;
        ShortBuffer sb = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder()).asShortBuffer();
        sb.put(indices);
        sb.position(0);

        createBuffer(target, sb, size, bufferId);
    }
    static void createBuffer(int target, Buffer buf, int size, int bufferId) {
        GLES20.glBindBuffer(target, bufferId);
        GLES20.glBufferData(target, size, buf, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(target, 0);
    }
}
