package com.example.songil.data

data class LikeData(val isLike : String, val totalLikeCnt : Int)

data class PageCnt(val totalPages : Int, val itemsPerPage : Int)

data class DeliveryStatus(val time : String, val where : String, val kind : String)

data class UserType(val type : Int)

data class ChangedItemFromAPI<T : Any>(val position : Int, val ApiResultCode : Int, var newData : T ?= null)