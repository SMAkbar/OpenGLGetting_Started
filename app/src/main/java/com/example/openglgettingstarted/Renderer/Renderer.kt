package com.example.openglgettingstarted.Renderer

import android.content.Context
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.example.openglgettingstarted.AirHockey.AirHockeyRenderer

class Renderer (_context : Context): GLSurfaceView.Renderer  {

    private val context : Context = _context
    private val airHockeyRenderer : AirHockeyRenderer = AirHockeyRenderer()

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f)

        airHockeyRenderer.prepareForTable(context)
    }

    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        airHockeyRenderer.drawTable()

    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        airHockeyRenderer.adjustRatio(width, height)
    }

}