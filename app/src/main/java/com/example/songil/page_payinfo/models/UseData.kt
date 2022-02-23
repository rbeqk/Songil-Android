package com.example.songil.page_payinfo.models

data class OrderDetailInfo(val craftIdx : Int, val mainImageUrl : String, val name : String, val artistIdx : Int, val artistName : String,
                           val price : Int, val amount : Int, val recipient : String, val phone : String, val address : String, val memo : String?,
                           val totalCraftPrice : Int, val basicShippingFee : Int, val extraShippingFee : Int, val pointDiscount : Int, val benefitDiscount : Int, val finalPrice : Int)