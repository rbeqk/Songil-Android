package com.songil.songil.page_mybenefit.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.Benefit

data class ResponseMyBenefit(val result : ArrayList<Benefit>) : BaseResponse()