package com.example.openglgettingstarted.AirHockey

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import android.opengl.Matrix.*
import android.util.Log
import com.example.openglgettingstarted.AirHockey.CoordinateData.CoordinateData
import com.example.openglgettingstarted.R
import com.example.openglgettingstarted.util.LoggerConfig
import com.example.openglgettingstarted.util.MatrixHelper
import com.example.openglgettingstarted.util.ShaderHelper
import com.example.openglgettingstarted.util.readTextFileFromResource
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class AirHockeyRenderer {

    // data to communicate to OpenGL
    private lateinit var vertexBuffer : FloatBuffer

    // shader source code
    private lateinit var vertexShaderSource : String
    private lateinit var fragmentShaderSource : String

    // getting coordinateData
    val coordinateData = CoordinateData()

    private fun uploadDataToBuffer() {
        vertexBuffer = ByteBuffer
            .allocateDirect(coordinateData.tableVertices.getSize() * BYTES_PER_FLOAT)
            .run {
                order(ByteOrder.nativeOrder())
                asFloatBuffer().apply {
                    put(coordinateData.tableVertices.getData())
                    position(0)
                }
            }
    }

    private fun readCode(context: Context){
        vertexShaderSource = readTextFileFromResource(context, R.raw.simple_vertex_shader)
        fragmentShaderSource = readTextFileFromResource(context, R.raw.simple_fragment_shader)

    }

    private fun compileCode() : Int{

        var vertexShader : Int = ShaderHelper.compileVertexShader(vertexShaderSource)
        var fragmentShader : Int = ShaderHelper.compileFragmentShader(fragmentShaderSource)


        val program : Int = ShaderHelper.linkProgram(vertexShader, fragmentShader)

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program)
        }
        return program
    }

    private fun setPositionAndColorLocation() {
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR)
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION)
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX)
    }

    private fun setAttributes() {
        vertexBuffer.position(0)
        GLES20.glVertexAttribPointer(
            aPositionLocation,
            POSITION_COMPONENT_COUNT,
            GLES20.GL_FLOAT,
            false,
            STRIDE,
            vertexBuffer)


        vertexBuffer.position(POSITION_COMPONENT_COUNT)
        GLES20.glVertexAttribPointer(
            aColorLocation,
            POSITION_COMPONENT_COUNT,
            GLES20.GL_FLOAT,
            false,
            STRIDE,
            vertexBuffer)
    }

    public fun prepareForTable(context: Context) {
        uploadDataToBuffer()

        readCode(context)

        program = compileCode()
        GLES20.glUseProgram(program)

        setPositionAndColorLocation()

        setAttributes()

        GLES20.glEnableVertexAttribArray(aPositionLocation)
        GLES20.glEnableVertexAttribArray(aColorLocation)
    }

    public fun drawTable(){

        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0)

//        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)

//        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

//        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1)

//        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1)


    }

    public fun adjustRatio(width : Int, height : Int) {
//        if (width > height) {
//            val aspectRatio = width.toFloat() / height.toFloat()
//            orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f)
//        } else {
//            val aspectRatio = height.toFloat() / width.toFloat()
//            orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f)
//        }

        val modelMatrix : FloatArray = FloatArray(16)
        MatrixHelper.perspectiveM(projectionMatrix, 45.toFloat(), width.toFloat() / height.toFloat(), 1f, 10f)
        setIdentityM(modelMatrix, 0)
//        translateM(modelMatrix, 0, 0f, 0f, -2f)

        // adding rotation to modelMatrix
        translateM(modelMatrix, 0, 0f, 0f, -2.5f)
        rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f)

        val temp : FloatArray = FloatArray(16)
        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0)
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.size)
    }

    companion object {
        private val BYTES_PER_FLOAT= 4
        private val POSITION_COMPONENT_COUNT = 4
        private val COLOR_COMPONENT_COUNT = 3
        private val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT

        private val A_POSITION : String = "a_Position"
        private val A_COLOR : String = "a_Color"
        private val U_MATRIX : String = "u_Matrix"

        private var program : Int = -1

        private var aPositionLocation : Int = -1
        private var aColorLocation : Int = -1
        private var uMatrixLocation : Int = -1

        private val projectionMatrix : FloatArray = FloatArray(16)
    }
}