package com.songil.songil.page_mypage

import com.songil.songil.page_mypage.models.ResponseGetMyInfo
import retrofit2.Response
import retrofit2.http.GET

interface MypageRetrofitInterface {
    @GET("my-page")
    suspend fun getMyInfo() : Response<ResponseGetMyInfo>
}