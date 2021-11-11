package com.example.songil.page_craft.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.CartIdx

data class ResponseProductDetail(val result : ProductDetailResult) : BaseResponse()

data class ResponseCarts(val result : CartIdx) : BaseResponse()