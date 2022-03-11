package com.songil.songil.page_artist

import com.songil.songil.page_artist.models.ResponseArtistInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistRetrofitInterface {
    @GET("artists/{artistIdx}")
    suspend fun getArtistInfo(@Path("artistIdx") artistIdx : Int) : Response<ResponseArtistInfo>

}