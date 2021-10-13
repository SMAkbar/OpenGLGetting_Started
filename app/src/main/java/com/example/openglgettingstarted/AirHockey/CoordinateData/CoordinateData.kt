package com.example.openglgettingstarted.AirHockey.CoordinateData

data class FloatVertices(val floatVertices : FloatArray) {
    fun getSize() : Int { return floatVertices.size }
    fun getData() : FloatArray { return floatVertices }
}

class CoordinateData {
    val tableVertices : FloatVertices = FloatVertices(floatArrayOf(
//        // Triangle 1
//        -0.5f, -0.5f,
//        0.5f, 0.5f,
//        -0.5f, 0.5f,
//
//        // Triangle 2
//        -0.5f, -0.5f,
//        0.5f, -0.5f,
//        0.5f, 0.5f,

        // Triangle Fan
        0f, 0f, 1f, 1f, 1f,
        -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
        0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
        -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

        // Line 1
        -0.5f, 0f, 1f, 0f, 0f,
        0.5f, 0f, 1f, 0f, 0f,

        // Mallets
        0f, -0.25f, 0f, 0f, 1f,
        0f, 0.25f, 1f, 0f, 0f
    )
    )
}