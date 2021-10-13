package com.example.openglgettingstarted

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.openglgettingstarted.GLSurfaceView.MyGLSurfaceView

class MainActivity : AppCompatActivity() {

    private lateinit var gLView: MyGLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gLView = MyGLSurfaceView(this)
        setContentView(gLView)

    }
}