package com.songil.songil.page_artist.subpage_article.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.PageCnt

data class ResponseArtistArticleItemCnt(val result : PageCnt) : BaseResponse()

data class ResponseArticleList(val result : ArticleAndArticleCnt) : BaseResponse()