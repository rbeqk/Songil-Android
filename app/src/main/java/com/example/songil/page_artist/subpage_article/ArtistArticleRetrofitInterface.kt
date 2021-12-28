package com.example.songil.page_artist.subpage_article

import com.example.songil.page_artist.subpage_article.models.ResponseArticleList
import com.example.songil.page_artist.subpage_article.models.ResponseArtistArticleItemCnt
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistArticleRetrofitInterface {
    @GET("artist/{artistIdx}/articles/page")
    suspend fun getArtistArticleCnt(@Path("artistIdx") artistIdx : Int) : Response<ResponseArtistArticleItemCnt>

    @GET("artist/{artistIdx}/articles")
    suspend fun getArticleList(@Path("artistIdx") artistIdx: Int, @Query("page") page : Int, @Query("sort") sort : String) : Response<ResponseArticleList>
}