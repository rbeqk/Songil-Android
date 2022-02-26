package com.example.songil.page_artistmanage.subpage_orderstat.models

data class OrderStatusArtistItemInfo(val orderDetailIdx : Int, var status : Int, val craftIdx : Int, val mainImageUrl : String, var name : String, val userIdx : Int, var userName : String)

data class GetArtistOrderStatusResponseBody(val createdAt : String, val order : ArrayList<OrderStatusArtistItemInfo>)