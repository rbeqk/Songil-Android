package com.example.songil.page_abtestwrite

import com.example.songil.page_abtestwrite.model.ResponseUploadAbTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface AbtestWriteRetrofitInterface {
    @Multipart
    @POST("with/ab-test")
    suspend fun postAbTest(@PartMap data : HashMap<String, RequestBody>, @Part image : ArrayList<MultipartBody.Part>) : Response<ResponseUploadAbTest>
}