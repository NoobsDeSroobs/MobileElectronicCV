package com.camerafeed.MyOpenGL;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by NoobsDeSroobs on 11-May-16.
 */
public class Shader {
    // THESE ARE ARBITRARY VALUES, the only constraints are
    // - must be different
    // - must be less than a maximum value
    static final int VERTEX_POS = 3;
    static final int NORMAL_POS = 4;
    static final int COLOR_POS = 5;
    static final int TEX_POS = 6;
    static final String TAG = "VBOTest";

    private int mProgramId;
    private int mViewProjectionLoc;
    private int mLightVectorLoc;
    private int mEnableLightLoc;
    private int mOffset;


    Shader() {
        mProgramId = loadProgram(kVertexShader, kFragmentShader);
        GLES20.glBindAttribLocation(mProgramId, Shader.VERTEX_POS, "position");
        GLES20.glBindAttribLocation(mProgramId, Shader.NORMAL_POS, "normal");
        GLES20.glBindAttribLocation(mProgramId, Shader.COLOR_POS, "color");
        GLES20.glLinkProgram(mProgramId);
        mViewProjectionLoc =
                GLES20.glGetUniformLocation(mProgramId, "worldViewProjection");
        mLightVectorLoc =
                GLES20.glGetUniformLocation(mProgramId, "lightVector");
        mEnableLightLoc =
                GLES20.glGetUniformLocation(mProgramId, "enableLight");
        mOffset =
                GLES20.glGetUniformLocation(mProgramId, "offset");


        // Other state.
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }

    public void use() {
        GLES20.glUseProgram(mProgramId);
    }
    public void setCamera(float[] viewProjectionMatrix) {
        GLES20.glUniformMatrix4fv(mViewProjectionLoc,
                1,
                false, // transpose isn't supported
                viewProjectionMatrix, 0);
    }
    public void setLight(float[] transformedLightVector) {
        GLES20.glUniform3fv(mLightVectorLoc, 1, transformedLightVector, 0);
    }
    public void setOffset(float[] offset) {
        GLES20.glUniform2fv(mOffset, 1, offset, 0);
    }
    public void enableLight(boolean val) {
        GLES20.glUniform1i(mEnableLightLoc, val ? 1 : 0);
    }

    static public void setViewPort(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }



    private static String kLogTag = "GDC11";

    private static int getShader(String source, int type) {
        int shader = GLES20.glCreateShader(type);
        if (shader == 0) return 0;

        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);
        int[] compiled = { 0 };
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e(kLogTag, GLES20.glGetShaderInfoLog(shader));
        }
        return shader;
    }

    public static int loadProgram(String vertexShader,
                                  String fragmentShader) {
        int vs = getShader(vertexShader, GLES20.GL_VERTEX_SHADER);
        int fs = getShader(fragmentShader, GLES20.GL_FRAGMENT_SHADER);
        if (vs == 0 || fs == 0) return 0;

        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vs);
        GLES20.glAttachShader(program, fs);
        GLES20.glLinkProgram(program);

        int[] linked = { 0 };
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linked, 0);
        if (linked[0] == 0) {
            Log.e(kLogTag, GLES20.glGetProgramInfoLog(program));
            return 0;
        }
        return program;
    }


    private static final String kVertexShader =
            "precision mediump float;                                   \n" +
            "uniform mat4 worldViewProjection;                          \n" +
            "uniform vec3 lightVector;                                  \n" +
            "attribute vec3 position;                                   \n" +
            "attribute vec3 normal;                                     \n" +
            "attribute vec3 color;                                      \n" +
            "uniform vec2 offset;                                     \n" +
            "varying vec3 colorFrag;                                    \n" +
            "varying float light;                                       \n" +
            "void main() {                                              \n" +
            // |lightVector| is in the model space, so the model
            // doesn't have to be transformed.
            "  colorFrag = color;                                       \n" +
            "  light = max(dot(normal, lightVector), 0.0) + 0.2;        \n" +
            "  gl_Position = worldViewProjection * vec4(position.x+offset.x, position.y+offset.y, position.z, 1.0); \n" +
            "}";

    private static final String kFragmentShader =
            "precision mediump float;                                   \n" +
            "uniform sampler2D textureSampler;                          \n" +
            "uniform int enableLight;                                   \n" +
            "varying vec3 colorFrag;                                    \n" +
            "varying float light;                                       \n" +
            "void main() {                                              \n" +
            "  if (1 == enableLight) {                                  \n" +
            "    gl_FragColor = light * vec4(colorFrag,1);                  \n" +
            "  } else {                                                 \n" +
            "    gl_FragColor = vec4(colorFrag,1);                          \n" +
            "  }                                                        \n" +
            // "  gl_FragColor = light * vec4(0.1,0.7,0.0,1);               \n" +
            "}";


    public void clearView() {
        int clearMask = GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT;
        GLES20.glClear(clearMask);
    }
}
