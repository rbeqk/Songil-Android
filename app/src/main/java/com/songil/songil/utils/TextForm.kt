package com.songil.songil.utils

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

fun checkPhoneString(phoneNumber : String) : Boolean {
    return (phoneNumber.matches(Regex("^01[016789]-\\d{3,4}-\\d{4}\$")) || phoneNumber.matches(Regex("^01[016789][0-9]{3,4}[0-9]{4}\$")))
}