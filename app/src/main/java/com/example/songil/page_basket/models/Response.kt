package com.example.songil.page_basket.models

import com.example.songil.config.BaseResponse

data class ResponseCartItems(val result : ArrayList<BasketItem>) : BaseResponse()