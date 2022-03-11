package com.songil.songil.page_article

import com.songil.songil.config.GlobalApplication

class ArticleRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(ArticleRetrofitInterface::class.java)

    suspend fun getArticleTitles() = retrofitInterface.getArticles()
}