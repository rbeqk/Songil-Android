package com.songil.songil.page_article

import com.songil.songil.page_article.models.ResponseArticles
import retrofit2.Response
import retrofit2.http.GET

interface ArticleRetrofitInterface {
    @GET("articles")
    suspend fun getArticles() : Response<ResponseArticles>
}