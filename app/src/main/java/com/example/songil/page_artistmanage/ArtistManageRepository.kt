package com.example.songil.page_artistmanage

import com.example.songil.config.GlobalApplication
import com.example.songil.page_artistmanage.models.ResponseGetArtistPageInfo

class ArtistManageRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ArtistManageRetrofitInterface::class.java)

    suspend fun getArtistPageInfo() : ResponseGetArtistPageInfo {
        val result = retrofit.getArtistPageInfo()
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}