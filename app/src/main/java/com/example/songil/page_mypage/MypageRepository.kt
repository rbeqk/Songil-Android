package com.example.songil.page_mypage

import com.example.songil.config.GlobalApplication
import com.example.songil.page_mypage.models.ResponseGetMyInfo
import retrofit2.Response

class MypageRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(MypageRetrofitInterface::class.java)

    suspend fun getMyInfo() : Response<ResponseGetMyInfo>{
        val result = retrofitInterface.getMyInfo()
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}