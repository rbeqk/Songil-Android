package com.example.songil.page_craft.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.CartIdx
import com.example.songil.data.ProductDetailInfo

data class ResponseProductDetail(val result : ProductDetailInfo) : BaseResponse()

data class ResponseCarts(val result : CartIdx) : BaseResponse()