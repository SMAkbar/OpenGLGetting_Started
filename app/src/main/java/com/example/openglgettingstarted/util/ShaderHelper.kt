package com.example.openglgettingstarted.util

import android.opengl.GLES20.*
import android.util.Log
import java.util.logging.Logger

class ShaderHelper {
    companion object {
        private val TAG: String = "HelperShader"

        public fun compileVertexShader(shaderCode: String): Int {
            return compileShader(GL_VERTEX_SHADER, shaderCode)
        }

        public fun compileFragmentShader(shaderCode: String): Int {
            return compileShader(GL_FRAGMENT_SHADER, shaderCode)
        }

        private fun compileShader(type: Int, shaderCode: String): Int {
            val shaderObjectId: Int = glCreateShader(type)

            if (shaderObjectId == 0) {
                if (LoggerConfig.ON) {
                    Log.w(TAG, "Could not create new shader.")
                }
                return 0
            }


            glShaderSource(shaderObjectId, shaderCode)

            val compileStatus: IntArray = intArrayOf(
                1
            )


            glCompileShader(shaderObjectId)

            glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0)
            if (LoggerConfig.ON) {
                Log.v(
                    TAG,
                    "Results of compiling source: " + "\n" + shaderCode + "\n:" + glGetShaderInfoLog(
                        shaderObjectId
                    )
                )
            }

            if (compileStatus[0] == 0) {
                glDeleteShader(shaderObjectId)

                if (LoggerConfig.ON) {
                    Log.w(TAG, "Compiler of shader failed.")
                }

                return 0
            }

            return shaderObjectId
        }

        public fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int) : Int {
            val programObjectId : Int = glCreateProgram()

            if (programObjectId == 0) {
                if (LoggerConfig.ON) {
                    Log.w(TAG, "Could not create new program")
                }

                return 0
            }

            glAttachShader(programObjectId, vertexShaderId)
            glAttachShader(programObjectId, fragmentShaderId)

            glLinkProgram(programObjectId)

            var linkStatus : IntArray = intArrayOf( 1 )
            glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0)

            if (LoggerConfig.ON) {
                Log.v(TAG, "Result of linking program: \n" + glGetProgramInfoLog(programObjectId))
            }

            if (linkStatus[0] == 0) {
                // If it failed, delete the program object.
                glDeleteProgram(programObjectId);
                if (LoggerConfig.ON) {
                    Log.w(TAG, "Linking of program failed.");
                }
                return 0;
            }

            return programObjectId
        }

        public fun validateProgram (programObjectId : Int) : Boolean {
            glValidateProgram(programObjectId)

            val validateStatus : IntArray = intArrayOf(1)
            glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0)
            Log.v(TAG, "Results of validating program: " + validateStatus[0] + "\nLog:" + glGetProgramInfoLog(programObjectId))

            return validateStatus[0] != 0
        }

        public fun consoleLog(string : String) {
            Log.v(TAG, string)
        }
    }
}