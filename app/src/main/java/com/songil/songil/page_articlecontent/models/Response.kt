package com.songil.songil.page_articlecontent.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.ArticleDetailInfo
import com.songil.songil.data.LikeData

data class ResponseArticleContent(val result : ArticleDetailInfo) : BaseResponse()

data class ResponseArticleLike(val result : LikeData) : BaseResponse()