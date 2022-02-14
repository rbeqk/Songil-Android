package com.example.songil.page_mybenefit.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.Benefit

data class ResponseMyBenefit(val result : ArrayList<Benefit>) : BaseResponse()