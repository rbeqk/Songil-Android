package com.example.songil.page_artist

import com.example.songil.config.GlobalApplication

class ArtistRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(ArtistRetrofitInterface::class.java)

    suspend fun getArtistInfo(artistIdx : Int) = retrofitInterface.getArtistInfo(artistIdx)
}