package com.songil.songil.page_artistmanage

import com.songil.songil.page_artistmanage.models.ResponseGetArtistPageInfo
import retrofit2.Response
import retrofit2.http.GET

interface ArtistManageRetrofitInterface {
    @GET("artist-page")
    suspend fun getArtistPageInfo() : Response<ResponseGetArtistPageInfo>
}