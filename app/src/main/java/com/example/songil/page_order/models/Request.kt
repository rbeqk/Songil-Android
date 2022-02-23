package com.example.songil.page_order.models

import com.example.songil.data.CraftAndAmount

data class RequestBodyGetOrder(val crafts : ArrayList<CraftAndAmount>)

data class RequestBodyPostExtraFee(val zipcode : String)

data class RequestBodyPostBenefit(val benefitIdx : Int?)

data class RequestBodyPostEtcInfo(var recipient : String = "", var phone : String = "", var address : String = "",
                                  var detailAddress : String = "", var memo : String? = null, var pointDiscount : Int = 0)

data class RequestBodyPostOrderVerification(val receiptId : String)