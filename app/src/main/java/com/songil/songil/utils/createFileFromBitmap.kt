package com.songil.songil.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

fun createFileFromBitmap(bitmap: Bitmap, context: Context, position : Int) : File {
    val file = File(context.filesDir, makeUploadFileName(position))
    val fileOutputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
    fileOutputStream.close()
    return file
}

// 파일명을 중복해서 저장하고싶지 않을 경우
fun makeImageFileName(position : Int) : String {
    val simpleFormat = SimpleDateFormat("yyyyMMdd_hhmmss", Locale.getDefault())
    val date = Date()
    return simpleFormat.format(date) + position.toString() + ".png"
}

// 업로드 한 이후 바로 제거하기 위해 static 한 파일명 생성
fun makeUploadFileName(position : Int) : String {
    return "tempImage$position.png"
}