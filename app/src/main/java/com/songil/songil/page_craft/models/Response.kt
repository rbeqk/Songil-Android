package com.songil.songil.page_craft.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.CartIdx
import com.songil.songil.data.CraftDetailInfo
import com.songil.songil.data.LikeData

data class ResponseCraftDetail(val result : CraftDetailInfo) : BaseResponse()

data class ResponseCarts(val result : CartIdx) : BaseResponse()

data class ResponseCraftLike(val result : LikeData) : BaseResponse()