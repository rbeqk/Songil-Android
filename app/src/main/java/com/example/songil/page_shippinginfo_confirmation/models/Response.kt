package com.example.songil.page_shippinginfo_confirmation.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.WaybillInfo

data class ResponseGetShippingInfo(val result : WaybillInfo) : BaseResponse()