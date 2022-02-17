package com.example.songil.page_basket.models

import com.example.songil.config.BaseResponse

data class ResponseGetCartItem(val result : ArrayList<CartItem>) : BaseResponse()

data class ResponsePatchCartItem(val result : Amount) : BaseResponse()