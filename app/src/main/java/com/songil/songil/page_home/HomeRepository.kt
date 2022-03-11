package com.songil.songil.page_home

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_home.models.ResponseGetHome
import retrofit2.Response

class HomeRepository {
    private val retrofitImpl = GlobalApplication.sRetrofit.create(HomeRetrofitInterface::class.java)

    suspend fun getHomeData() : Response<ResponseGetHome>{
        val result = retrofitImpl.getHomeData()
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}