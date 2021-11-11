package com.example.songil.page_article.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.SimpleArticle

data class ResponseArticles(val result : ArrayList<SimpleArticle>) : BaseResponse()