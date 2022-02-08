package com.example.songil.page_abtestwrite

import com.example.songil.config.GlobalApplication
import com.example.songil.page_abtestwrite.model.ResponseUploadAbTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class AbtestWriteRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(AbtestWriteRetrofitInterface::class.java)

    suspend fun uploadAbTest(data : HashMap<String, RequestBody>, image : ArrayList<MultipartBody.Part>) : Response<ResponseUploadAbTest> {
        val result = retrofitInterface.postAbTest(data, image)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}