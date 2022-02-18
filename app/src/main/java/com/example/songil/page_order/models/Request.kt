package com.example.songil.page_order.models

import com.example.songil.data.CraftAndAmount

data class RequestBodyGetOrder(val crafts : ArrayList<CraftAndAmount>)

data class RequestBodyPostExtraFee(val zipcode : String)