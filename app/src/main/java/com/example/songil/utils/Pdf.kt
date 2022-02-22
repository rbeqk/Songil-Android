package com.example.songil.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun PdfRenderer.Page.renderAndClose(width: Int) = use {
    val bitmap = createBitmap(width)
    render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
    bitmap
}

private fun PdfRenderer.Page.createBitmap(bitmapWidth: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(
            bitmapWidth, (bitmapWidth.toFloat() / width * height).toInt(), Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.WHITE)
    canvas.drawBitmap(bitmap, 0f, 0f, null)

    return bitmap
}

fun copyInputStreamToFile(inputStream : InputStream, file : File){
    val outputStream = FileOutputStream(file)
    Log.d("inputStream", "${inputStream.available()}")
    val bytes = ByteArray(inputStream.available() + 1)
    var read : Int
    for (i in 0..inputStream.available()){
        read = inputStream.read(bytes)
        if (read != -1)
            outputStream.write(bytes, 0, read)
    }
}