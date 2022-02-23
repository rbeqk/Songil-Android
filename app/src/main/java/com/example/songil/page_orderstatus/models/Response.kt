package com.example.songil.page_orderstatus.models

import com.example.songil.config.BaseResponse

data class ResponseGetOrderStatus(val result : ArrayList<OrderStatusInfo>) : BaseResponse()