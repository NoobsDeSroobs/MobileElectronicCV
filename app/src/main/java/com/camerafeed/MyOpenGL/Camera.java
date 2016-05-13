package com.camerafeed.MyOpenGL;

import android.graphics.PointF;
import android.opengl.Matrix;
import android.util.Log;

/**
 * Created by NoobsDeSroobs on 11-May-16.
 */
public class Camera {
    private float mPhi, mZ = 3.5f;
    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mViewProjectionMatrix = new float[16];
    private float[] mCameraLocation = {0, 0, 6, 1};
    private float[] mCameraLocationPrev = {0, 0, 6, 1};
    private float[] mFocusLocation = {0, 0, 1, 1};
    private float[] mUpVector = {0, 1, 0, 1};


    public Camera(PointF screenSize){
        perspective((int)screenSize.x, (int)screenSize.y);
    }

    // Updates mViewProjectionMatrix with the current camera position.
    public void updateMatrices() {
        //setLookAtM(float[] rm, int rmOffset, float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ)
        Matrix.setLookAtM(mViewMatrix, 0, mCameraLocation[0], mCameraLocation[1], mCameraLocation[2],
                0, 0, 0, //mCameraLocation[0]-mFocusLocation[0], mCameraLocation[1]-mFocusLocation[1], mCameraLocation[2]-mFocusLocation[2],
                mUpVector[0], mUpVector[1], mUpVector[2]);
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
        updateMatrices();
        shader.setCamera(mViewProjectionMatrix);
    }

    public void saveCamera(){
        double camDist = Math.sqrt(Math.pow(mCameraLocation[0], 2) + Math.pow(mCameraLocation[1], 2) + Math.pow(mCameraLocation[2], 2));
        if (camDist > 0.1) {
            mCameraLocationPrev[0] = mCameraLocation[0];
            mCameraLocationPrev[1] = mCameraLocation[1];
            mCameraLocationPrev[2] = mCameraLocation[2];
        }
    }

    public void zoom(float scale) {
        mCameraLocation[0] = mCameraLocationPrev[0] * scale;
        mCameraLocation[1] = mCameraLocationPrev[1] * scale;
        mCameraLocation[2] = mCameraLocationPrev[2] * scale;
    }

    public void rotate(PointF dir) {
//        if (dir.x < 0.2 && dir.x > -0.2 && dir.y < 0.2 && dir.y > 0.2){
//            return;
//        }
        float[] tempM = new float[16];
        Matrix.setIdentityM(tempM, 0);
        float[] axis = new float[4];
        axis[3] = 1;
        cross(mCameraLocation, mUpVector, axis);
        normalize(axis);
        Matrix.rotateM(tempM, 0, dir.y, axis[0], axis[1], axis[2]);
        Matrix.multiplyMV(mCameraLocation, 0, tempM, 0, mCameraLocation, 0);
        Matrix.setIdentityM(tempM, 0);
        Matrix.rotateM(tempM, 0, -dir.x, mUpVector[0], mUpVector[1], mUpVector[2]);
        Matrix.multiplyMV(mCameraLocation, 0, tempM, 0, mCameraLocation, 0);

        saveCamera();
    }

    private void normalize(float[] axis) {
        float length = (float)Math.sqrt(Math.pow(axis[0], 2) + Math.pow(axis[1], 2) + Math.pow(axis[2], 2));
        axis[0] /= length;
        axis[1] /= length;
        axis[2] /= length;
    }

    void cross(float[] p1, float[] p2, float[] result) {
        result[0] = p1[1] * p2[2] - p2[1] * p1[2];
        result[1] = p1[2] * p2[0] - p2[2] * p1[0];
        result[2] = p1[0] * p2[1] - p2[0] * p1[1];
    }
}
