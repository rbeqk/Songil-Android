package com.songil.songil.page_order.models

import com.songil.songil.config.BaseResponse

data class ResponseGetOrder(val result : GetOrderInfoResponseBody) : BaseResponse()

data class ResponsePostExtraFee(val result : PostExtraFeeResponseBody) : BaseResponse()

data class ResponsePostBenefit(val result : PostBenefitResponseBody) : BaseResponse()

data class ResponsePostEtcInfo(val result : PostEtcInfoResponseBody) : BaseResponse()