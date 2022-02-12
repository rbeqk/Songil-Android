package com.example.songil.page_shop.shop_category.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.Craft2
import com.example.songil.data.PageCnt

data class ResponsePopularCraft(val result : ArrayList<Craft2>) : BaseResponse()

data class ResponseCategoryCraft(val result : ResultOfCategoryCraft) : BaseResponse()

data class ResponseStartPageIdx(val result : PageCnt) : BaseResponse()