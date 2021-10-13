package com.example.openglgettingstarted.AirHockey

import android.content.Context
import android.opengl.GLES20
import com.example.openglgettingstarted.AirHockey.CoordinateData.CoordinateData
import com.example.openglgettingstarted.R
import com.example.openglgettingstarted.util.LoggerConfig
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
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR)
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
    }

    private fun setAttributes() {
        vertexBuffer.position(0)
        GLES20.glVertexAttribPointer(
            aPositionLocation,
            POSITION_COMPONENT_COUNT,
            GLES20.GL_FLOAT,
            false,
            0,
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
    }

    public fun drawTable(){
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)

        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1)

        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1)
    }

    companion object {
        private val BYTES_PER_FLOAT= 4
        private val POSITION_COMPONENT_COUNT = 2

        private val U_COLOR : String = "u_Color"
        private val A_POSITION : String = "a_Position"

        private var program : Int = -1

        private var uColorLocation : Int = -1
        private var aPositionLocation : Int = -1
    }
}