package com.songil.songil.page_abtestwrite

import com.songil.songil.config.BaseResponse
import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_abtestwrite.model.AbTestContent
import com.songil.songil.page_abtestwrite.model.ResponseUploadAbTest
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

    suspend fun modifyAbTest(abTestIdx : Int, content : String) : Response<BaseResponse> {
        val result = retrofitInterface.patchAbTest(abTestIdx, AbTestContent(content))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}