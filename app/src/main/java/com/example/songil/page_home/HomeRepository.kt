package com.example.songil.page_home

import com.example.songil.config.GlobalApplication
import com.example.songil.page_home.models.HomeData
import com.example.songil.page_home.models.ResponseGetHome
import retrofit2.Response

class HomeRepository {
    private val retrofitImpl = GlobalApplication.sRetrofit.create(HomeRetrofitInterface::class.java)

    suspend fun getHomeData() : Response<ResponseGetHome>{
        val result = retrofitImpl.getHomeData()
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}