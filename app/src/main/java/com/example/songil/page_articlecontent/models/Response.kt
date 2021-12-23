package com.example.songil.page_articlecontent.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.ArticleDetailInfo
import com.example.songil.data.LikeData

data class ResponseArticleContent(val result : ArticleDetailInfo) : BaseResponse()

data class ResponseArticleLike(val result : LikeData) : BaseResponse()