package com.camerafeed.MyOpenGL;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by IMERSO on 12/8/2015.
 */
public class Box3D {
    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;
    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float coords[] = {
            // in counterclockwise order:
            -0.5f, -0.5f, -0.5f, //BLB 0
            -0.5f, 0.5f, -0.5f,  //BLF 1
            0.5f, -0.5f, -0.5f,  //BRB 2
            0.5f, 0.5f, -0.5f,   //BRF 3
            -0.5f, -0.5f, 0.5f,  //TLB 4
            -0.5f, 0.5f, 0.5f,   //TLF 5
            0.5f, -0.5f, 0.5f,   //TRB 6
            0.5f, 0.5f, 0.5f     //TRF 7
    };
    static short indices[] = {
            // in counterclockwise order:
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
            7, 4, 6
    };
    private final int vertexCount = coords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private int vbo[] = {0};
    private int ibo[] = {0};

    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Box3D() {}

    public void setup(){
//        GLES20.glGetError();
//        GLES20.glGenBuffers(1, vbo, 0);
//        GLES20.glGenBuffers(1, ibo, 0);
//
//
//
//        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
//        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
//        // initialize vertex byte buffer for shape coordinates
//        ByteBuffer vb = ByteBuffer.allocateDirect(
//                // (number of coordinate values * 4 bytes per float)
//                coords.length * 4);
//        vb.order(ByteOrder.nativeOrder());
//
//        vertexBuffer = vb.asFloatBuffer();
//        vertexBuffer.put(coords);
//        vertexBuffer.position(0);
//
//        ByteBuffer ib = ByteBuffer.allocateDirect(indices.length * 4);
//        ib.order(ByteOrder.nativeOrder());
//
//        indexBuffer = ib.asShortBuffer();
//        indexBuffer.put(indices);
//        indexBuffer.position(0);
//
//
//        // prepare shaders and OpenGL program
//        int vertexShader = GoLRenderer.loadShader(
//                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
//        int fragmentShader = GoLRenderer.loadShader(
//                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
//
//        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
//        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
//        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
//
//
//        // Enable a handle to the triangle vertices
//        GLES20.glEnableVertexAttribArray(mPositionHandle);
//        GLES20.glLinkProgram(mProgram);
//        GoLRenderer.checkGlError("Linked shaders");
//        // get handle to vertex shader's vPosition member
//        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
//        GoLRenderer.checkGlError("Get attrib");
//
//
//        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
//        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);
//
//        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
//        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity() * 2, indexBuffer, GLES20.GL_STATIC_DRAW);
//
//        GoLRenderer.checkGlError("Buffered buffers");                 // create OpenGL program executables
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment
//        GLES20.glUseProgram(mProgram);
//
//        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
//
//
//        // Prepare the triangle coordinate data
//        GLES20.glVertexAttribPointer(
//                mPositionHandle, COORDS_PER_VERTEX,
//                GLES20.GL_FLOAT, false,
//                0, vertexBuffer);
//
//
//        GoLRenderer.checkGlError("Loaded all data");
//        // get handle to fragment shader's vColor member
//        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
//
//        // Set color for drawing the triangle
//        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
//
//        // get handle to shape's transformation matrix
//        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//        //GoLRenderer.checkGlError("glGetUniformLocation");
//
//        // Apply the projection and view transformation
//        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
//        //GoLRenderer.checkGlError("glUniformMatrix4fv");
//
//        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
//        // Draw the triangle
//        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_SHORT, 0);
//
//        // Disable vertex array
//        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}


