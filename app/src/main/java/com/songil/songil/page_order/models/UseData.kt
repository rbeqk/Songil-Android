package com.songil.songil.page_order.models

import com.songil.songil.data.Craft4

data class GetOrderInfoResponseBody(val orderIdx : Int, val craft : ArrayList<Craft4>, val point : Int, val totalCraftPrice : Int, val totalBasicShippingFee : Int, var finalPrice : Int)

data class PostExtraFeeResponseBody(val totalExtraShippingFee : Int, val finalPrice : Int)

data class PostBenefitResponseBody(val benefitIdx : Int?, val title : String?, val benefitDiscount : Int, val finalPrice : Int)

data class PostEtcInfoResponseBody(val finalPrice: Int)

class PriceData(var craftTotalPrice : Int = 0, var couponName : String? = null, var couponDiscount : Int = 0, var shippingFee : Int = 0, var extraShippingFee : Int = 0,
                var usePoint : Int = 0, var havePoint : Int = 0) {

    fun applyUserPoint(inputPoint : Int) {
        usePoint = if (havePoint > inputPoint) { inputPoint }
        else { havePoint }
    }

    fun calTotalPrice() : Int {
        return (craftTotalPrice - couponDiscount - usePoint + shippingFee + extraShippingFee)
    }
}

data class ShippingInfo(var recipient : String = "", var phone : String = "", var address : String = "", var detailAddress : String = "", var memo : String = "", var zipCode : String = "")