package com.example.songil.page_myfavorite_article.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.ItemArticle

// 아마 작가페이지-작가아티클과 동일한 response 로 예상.
data class ResponseGetFavoriteArticle(val result : ArrayList<ItemArticle>) : BaseResponse()