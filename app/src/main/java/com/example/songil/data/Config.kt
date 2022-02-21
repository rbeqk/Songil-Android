package com.example.songil.data

data class LikeData(val isLike : String, val totalLikeCnt : Int)

data class PageCnt(val totalPages : Int, val itemsPerPage : Int)

data class DeliveryStatus(val time : String, val currentPosition : String, val deliveryStatus : String)

data class UserType(val type : Int)