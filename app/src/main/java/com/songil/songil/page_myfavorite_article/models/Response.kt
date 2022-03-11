package com.songil.songil.page_myfavorite_article.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.ItemArticle

// 아마 작가페이지-작가아티클과 동일한 response 로 예상.
data class ResponseGetFavoriteArticle(val result : ArrayList<ItemArticle>) : BaseResponse()