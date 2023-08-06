package com.raydev.shared.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
object FileUtils {
    fun getJsonStringFromAssets(context: Context, assetPath: String, progress:(Int)->Any?={}):String{
        val `is`: InputStream = context.assets.open(assetPath)
        ProgressInputStream(`is`).addListener {
            progress.invoke(it)
        }
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        `is`.use { `is` ->
            val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        return writer.toString()
    }
}