package com.songil.songil.page_myfavorite_article

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_myfavorite_article.models.ResponseGetFavoriteArticle
import retrofit2.Response

class MyFavoriteArticleRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(MyFavoriteArticleRetrofitInterface::class.java)

    suspend fun getFavoriteArticle(pageIdx : Int) : Response<ResponseGetFavoriteArticle> {
        val result = retrofitInterface.getFavoriteArticle(page = pageIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}