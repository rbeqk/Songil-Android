package com.songil.songil.page_orderstatus.models

import com.songil.songil.config.BaseResponse

data class ResponseGetOrderStatus(val result : ArrayList<OrderStatusInfo>) : BaseResponse()