package com.example.songil.page_artistmanage.subpage_asklist

import com.example.songil.page_artistmanage.subpage_asklist.models.ResponseArtistAskPageCnt
import com.example.songil.page_artistmanage.subpage_asklist.models.ResponseAskList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistManageAskRetrofitInterface {
    @GET("artist-page/ask/page")
    suspend fun getTotalPageCnt() : Response<ResponseArtistAskPageCnt>

    @GET("artist-page/ask")
    suspend fun getAskList(@Query("page") page : Int) : Response<ResponseAskList>
}