package com.songil.songil.page_artist.subpage_article

import com.songil.songil.config.GlobalApplication

class ArtistArticleRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(ArtistArticleRetrofitInterface::class.java)

    suspend fun getArtistPageCnt(artistIdx : Int) = retrofitInterface.getArtistArticleCnt(artistIdx)

    suspend fun getArticleList(artistIdx : Int, page : Int, sort : String) = retrofitInterface.getArticleList(artistIdx, page, sort)
}