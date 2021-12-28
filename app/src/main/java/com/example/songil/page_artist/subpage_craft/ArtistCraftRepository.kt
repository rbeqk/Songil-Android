package com.example.songil.page_artist.subpage_craft

import com.example.songil.config.GlobalApplication

class ArtistCraftRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(ArtistCraftRetrofitInterface::class.java)

    suspend fun getCraftList(artistIdx : Int, page : Int, sort : String) = retrofitInterface.getCraftList(artistIdx, page, sort)

    suspend fun getCraftPageCnt(artistIdx : Int) = retrofitInterface.getArtistCraftCnt(artistIdx)
}