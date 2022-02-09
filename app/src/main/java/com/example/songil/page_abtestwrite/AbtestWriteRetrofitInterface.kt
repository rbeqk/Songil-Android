package com.example.songil.page_abtestwrite

import com.example.songil.config.BaseResponse
import com.example.songil.page_abtestwrite.model.AbTestContent
import com.example.songil.page_abtestwrite.model.ResponseUploadAbTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AbtestWriteRetrofitInterface {
    @Multipart
    @POST("with/ab-test")
    suspend fun postAbTest(@PartMap data : HashMap<String, RequestBody>, @Part image : ArrayList<MultipartBody.Part>) : Response<ResponseUploadAbTest>

    @PATCH("with/ab-test/{abTestIdx}")
    suspend fun patchAbTest(@Path("abTestIdx") abTestIdx : Int, @Body params : AbTestContent) : Response<BaseResponse>
}