package com.songil.songil.page_shippinginfo_confirmation.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.WaybillInfo

data class ResponseGetShippingInfo(val result : WaybillInfo) : BaseResponse()