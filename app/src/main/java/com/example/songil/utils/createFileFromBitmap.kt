package com.example.songil.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

fun createFileFromBitmap(bitmap: Bitmap, context: Context, position : Int) : File {
    val file = File(context.filesDir, makeImageFileName(position))
    val fileOutputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
    fileOutputStream.close()
    return file
}

fun makeImageFileName(position : Int) : String {
    val simpleFormat = SimpleDateFormat("yyyyMMdd_hhmmss", Locale.getDefault())
    val date = Date()
    return simpleFormat.format(date) + position.toString() + ".png"
}