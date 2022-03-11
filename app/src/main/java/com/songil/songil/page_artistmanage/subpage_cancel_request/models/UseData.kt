package com.songil.songil.page_artistmanage.subpage_cancel_request.models

data class CancelRequest(val orderDetailIdx : Int, var status : Int, val craftIdx : Int, val mainImageUrl : String, var name : String,
                         val userIdx : Int, var userName : String, val reason : String, var canChangeStatus : String)

data class GetCancelRequestItemResponseBody(val createdAt : String, val order : ArrayList<CancelRequest>)