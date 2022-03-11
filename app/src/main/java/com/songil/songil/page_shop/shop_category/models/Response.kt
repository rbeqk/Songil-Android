package com.songil.songil.page_shop.shop_category.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.Craft2
import com.songil.songil.data.PageCnt

data class ResponsePopularCraft(val result : ArrayList<Craft2>) : BaseResponse()

data class ResponseCategoryCraft(val result : ResultOfCategoryCraft) : BaseResponse()

data class ResponseStartPageIdx(val result : PageCnt) : BaseResponse()