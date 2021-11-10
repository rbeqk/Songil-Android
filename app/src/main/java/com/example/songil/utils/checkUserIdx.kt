package com.example.songil.utils

import com.example.songil.config.GlobalApplication

fun checkUserIdx() : Boolean {
    return (GlobalApplication.globalSharedPreferences.getInt(GlobalApplication.USER_IDX, 0) != 0)
}