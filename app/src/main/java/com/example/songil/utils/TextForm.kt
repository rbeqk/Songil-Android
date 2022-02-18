package com.example.songil.utils

import java.text.DecimalFormat

fun changeToPriceFormKr(price : Int, isMinus : Boolean ?= null) : String {
    return when (isMinus) {
        true -> {
            "-" + DecimalFormat("#,###").format(price)
        }
        false -> {
            "+" + DecimalFormat("#,###").format(price)
        }
        else -> {
            DecimalFormat("#,###").format(price)
        }
    }
}

fun changeToPriceForm(price : Int) : String {
    return DecimalFormat("#,###").format(price)
}
