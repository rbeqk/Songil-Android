package com.example.songil.page_artist.subpage_article.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.PageCnt

data class ResponseArtistArticleItemCnt(val result : PageCnt) : BaseResponse()

data class ResponseArticleList(val result : ArticleAndArticleCnt) : BaseResponse()