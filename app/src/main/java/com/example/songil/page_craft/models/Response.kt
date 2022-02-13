package com.example.songil.page_craft.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.CartIdx
import com.example.songil.data.CraftDetailInfo
import com.example.songil.data.LikeData

data class ResponseCraftDetail(val result : CraftDetailInfo) : BaseResponse()

data class ResponseCarts(val result : CartIdx) : BaseResponse()

data class ResponseCraftLike(val result : LikeData) : BaseResponse()