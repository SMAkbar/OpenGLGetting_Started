package com.example.openglgettingstarted.GLSurfaceView


import android.content.Context
import android.opengl.GLSurfaceView
import com.example.openglgettingstarted.Renderer.Renderer


class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: Renderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        renderer = Renderer(context)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)

        // Render the view only when there is a change in the drawing data
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

}