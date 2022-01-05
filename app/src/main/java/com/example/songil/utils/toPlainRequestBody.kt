package com.example.songil.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun toPlainRequestBody(text : String) = text.toRequestBody("text/plain".toMediaTypeOrNull())

fun toPlainRequestBody(array : ArrayList<String>) : ArrayList<MultipartBody.Part> {
    val tags = ArrayList<MultipartBody.Part>()
    for (item in array){
        tags.add(MultipartBody.Part.Companion.createFormData("tag", item))
    }
    return tags
}