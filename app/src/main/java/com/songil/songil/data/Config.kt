package com.songil.songil.data

data class LikeData(val isLike : String, val totalLikeCnt : Int)

data class PageCnt(val totalPages : Int, val itemsPerPage : Int)

data class DeliveryStatus(val time : ArrayList<String>, val where : String, val kind : String)

data class UserType(val type : Int)

data class ChangedItemFromAPI<T : Any>(val position : Int, val ApiResultCode : Int, var newData : T ?= null)

data class WaybillInfo(val year : Int, val month : Int, val day : Int, val tCode : String, val tInvoice : String)