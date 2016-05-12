/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.imerso.camerafeed.MyOpenGL;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    // OpenGL state stuff.
    private Shader mShader;
    private Camera mCamera;

    VBO mVBO1, mVBO2, mVBO3;

    private float[] mLightVector = { 2/3.f, 1/3.f, 2/3.f };  // Needs to be normalized
    private float[] mTransformedLightVector = new float[3];

    private void updateLightVector() {

        // Transform the light vector into model space. Since mViewMatrix
        // is orthogonal, the reverse transform can be done by multiplying
        // with the transpose.

        float[] viewMatrix = mCamera.viewMatrix();

        mTransformedLightVector[0] =
                viewMatrix[0] * mLightVector[0] +
                        viewMatrix[1] * mLightVector[1] +
                        viewMatrix[2] * mLightVector[2];
        mTransformedLightVector[1] =
                viewMatrix[4] * mLightVector[0] +
                        viewMatrix[5] * mLightVector[1] +
                        viewMatrix[6] * mLightVector[2];
        mTransformedLightVector[2] =
                viewMatrix[8] * mLightVector[0] +
                        viewMatrix[9] * mLightVector[1] +
                        viewMatrix[10] * mLightVector[2];
    }

    // This is called continuously to render.
    @Override
    public void onDrawFrame(GL10 unused) {

        mShader.use();
        mShader.clearView();
        mCamera.use(mShader);
        mShader.setLight(mTransformedLightVector);

        // VBO
        mShader.enableLight(true);

        mShader.setColor(red);
        //mVBO1.draw();

        mShader.setColor(gold);
        //mVBO2.draw();

        mShader.enableLight(false);
        mShader.setColor(brown);
        mVBO3.draw();

    }
    static float[] green = {0.2f,1,0.2f};
    static float[] brown = {0.7f,0.4f,0.2f};
    static float[] red = {0.9f,0,0};
    static float[] gold = {0.9f,0.8f,0.1f};
    static float[] black = {0,0,0};


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // CREATE GEOMETRY
        // NEVER load stuff on the render thread in real life!
        // You'd call fc.map() and b.load() on a loader thread, and
        // only then upload that to GL once it's done.

        mShader = new Shader();
        mCamera = new Camera();

        //GeoData data = GeoData.halfpipe();
        //mVBO1 = new VBO(data.mVertices, data.mIndices, GLES20.GL_TRIANGLE_STRIP, true, false, -1);

        //data = GeoData.circle();
        //mVBO2 = new VBO(data.mVertices, data.mIndices, GLES20.GL_TRIANGLE_FAN, true, false, -1);

        GeoData data = GeoData.grid();
        mVBO3 = new VBO(data.mVertices, data.mIndices, GLES20.GL_LINES, false, false, -1);
    }

    // This is called when the surface changes, e.g. after screen rotation.
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        mCamera.perspective(width, height);

        updateLightVector();

        // Necessary if the manifest contains |android:configChanges="orientation"|.
        Shader.setViewPort(width, height);
    }

}