package com.songil.songil.page_myfavorite_article

import com.songil.songil.page_myfavorite_article.models.ResponseGetFavoriteArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyFavoriteArticleRetrofitInterface {
    @GET("my-page/articles/liked")
    suspend fun getFavoriteArticle(@Query("page") page : Int) : Response<ResponseGetFavoriteArticle>
}