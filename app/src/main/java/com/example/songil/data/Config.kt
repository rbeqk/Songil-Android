package com.example.songil.data

data class LikeData(val isLike : String, val totalLikeCnt : Int)

data class PageCnt(val totalPages : Int)

data class DeliveryStatus(val time : String, val currentPosition : String, val deliveryStatus : String)