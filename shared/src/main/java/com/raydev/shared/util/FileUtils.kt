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
    fun getJsonStringFromAssets(context: Context, assetPath: String, progress: (Int) -> Any? = {}): String {
        val inputStream: InputStream = context.assets.open(assetPath)
        ProgressInputStream(inputStream).addListener {
            progress.invoke(it)
        }
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        inputStream.use { input ->
            val reader: Reader = BufferedReader(InputStreamReader(input, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        return writer.toString()
    }
}
