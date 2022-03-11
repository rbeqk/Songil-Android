package com.songil.songil.page_article.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.SimpleArticle

data class ResponseArticles(val result : ArrayList<SimpleArticle>) : BaseResponse()