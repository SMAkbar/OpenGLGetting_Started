package com.example.openglgettingstarted.util

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.example.openglgettingstarted.util.ShaderHelper.Companion.consoleLog
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.RuntimeException
import java.lang.StringBuilder
import kotlin.reflect.typeOf

public fun readTextFileFromResource(context: Context, resourceId: Int) : String {
    val body: StringBuilder = StringBuilder()

    try {
        val inputStream : InputStream = context.getResources().openRawResource(resourceId)
        val inputStreamReader : InputStreamReader = InputStreamReader(inputStream)
        val bufferReader : BufferedReader = BufferedReader(inputStreamReader)

        var nextLine : String?

        nextLine = bufferReader.readLine()
        while(nextLine != null) {
            body.append(nextLine!!)
            body.append('\n')
            nextLine = bufferReader.readLine()
        }
    } catch (e: IOException) {
        throw RuntimeException("Could not open resource: " + resourceId, e)
    } catch (nfe: Resources.NotFoundException){
        throw RuntimeException("Resource not found: " + resourceId, nfe)
    }

    return body.toString()
}