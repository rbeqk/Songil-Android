package com.example.songil.page_order.models

import com.example.songil.config.BaseResponse

data class ResponseGetOrder(val result : GetOrderInfoResponseBody) : BaseResponse()

data class ResponsePostExtraFee(val result : PostExtraFeeResponseBody) : BaseResponse()

data class ResponsePostBenefit(val result : PostBenefitResponseBody) : BaseResponse()