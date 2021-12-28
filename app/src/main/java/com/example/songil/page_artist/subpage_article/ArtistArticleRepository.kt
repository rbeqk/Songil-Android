package com.example.songil.page_artist.subpage_article

import com.example.songil.config.GlobalApplication

class ArtistArticleRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(ArtistArticleRetrofitInterface::class.java)

    suspend fun getArtistPageCnt(artistIdx : Int) = retrofitInterface.getArtistArticleCnt(artistIdx)

    suspend fun getArticleList(artistIdx : Int, page : Int, sort : String) = retrofitInterface.getArticleList(artistIdx, page, sort)
}