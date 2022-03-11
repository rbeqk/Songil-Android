package com.songil.songil.page_articlecontent

import com.songil.songil.page_articlecontent.models.ResponseArticleContent
import com.songil.songil.page_articlecontent.models.ResponseArticleLike
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ArticleContentRetrofitInterface {
    @GET("articles/{articleIdx}")
    suspend fun getArticleContents(@Path("articleIdx") articleIdx : Int) : Response<ResponseArticleContent>

    @PATCH("articles/{articleIdx}/like")
    suspend fun changeArticleLike(@Path("articleIdx") articleIdx : Int) : Response<ResponseArticleLike>
}