package com.example.imerso.camerafeed.MyOpenGL;

import android.opengl.Matrix;

/**
 * Created by NoobsDeSroobs on 11-May-16.
 */
public class Camera {
    private float mPhi, mZ = 3.5f;
    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mViewProjectionMatrix = new float[16];


    // Updates mViewProjectionMatrix with the current camera position.
    public void updateMatrices() {
        //setLookAtM(float[] rm, int rmOffset, float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 60.0f, 0, 0, 0, 0, 1f, 0);
        //Matrix.translateM(mViewMatrix, 0, 0, 0, -mZ);
        //Matrix.rotateM(mViewMatrix, 0, mPhi, 0, 1, 0);
        //Matrix.rotateM(mViewMatrix, 0, 45, 1, 0, 0);
        Matrix.multiplyMM(
                mViewProjectionMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }

    public float[] viewMatrix() {
        return mViewMatrix;
    }

    public void perspective(int width, int height) {
        float aspect = width / (float)height;
        perspectiveM(
                mProjectionMatrix,
                (float)Math.toRadians(45),
                aspect, 0.1f, 100f);
        // aspect, 0.5f, 5.f);
        updateMatrices();
    }

    // Like gluPerspective(), but writes the output to a Matrix.
    static private void perspectiveM(
            float[] m, float angle, float aspect, float near, float far) {
        float f = (float)Math.tan(0.5 * (Math.PI - angle));
        float range = near - far;

        m[0] = f / aspect;
        m[1] = 0;
        m[2] = 0;
        m[3] = 0;

        m[4] = 0;
        m[5] = f;
        m[6] = 0;
        m[7] = 0;

        m[8] = 0;
        m[9] = 0;
        m[10] = far / range;
        m[11] = -1;

        m[12] = 0;
        m[13] = 0;
        m[14] = near * far / range;
        m[15] = 0;
    }

    public void use(Shader shader) {
        shader.setCamera(mViewProjectionMatrix);
    }
}