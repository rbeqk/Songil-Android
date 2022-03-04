package com.example.songil.page_mycomment.models

import com.example.songil.config.BaseResponse

data class ResponseGetMyWritableComment(val result : ArrayList<ResponseBodyGetWritableComment>) : BaseResponse()

data class ResponseGetMyWrittenComment(val result : ArrayList<ResponseBodyGetWrittenComment>) : BaseResponse()