package com.songil.songil.page_articlecontent

import com.songil.songil.config.GlobalApplication

class ArticleContentRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(ArticleContentRetrofitInterface::class.java)

    suspend fun getArticleContent(articleIdx : Int) = retrofitInterface.getArticleContents(articleIdx)

    suspend fun patchArticleLike(articleIdx : Int) = retrofitInterface.changeArticleLike(articleIdx)
}