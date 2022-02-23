package com.example.songil.page_orderstatus.models

data class OrderStatusInfo(val createdAt : String, val orderIdx : Int, val orderDetail : ArrayList<OrderStatusItemInfo>)

data class OrderStatusItemInfo(val orderDetailIdx : Int, var status : Int, val craftIdx : Int, val mainImageUrl : String, val name : String,
                               val artistIdx : Int, val artistName : String, val price : Int, val amount : Int, var canReqCancel : String?, var canReqReturn : String?, var canWriteComment : String)