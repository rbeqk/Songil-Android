package com.example.songil.utils

import android.app.Activity
import android.os.Build
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.example.songil.R

fun setStatusBar(activity : Activity, isDark : Boolean){
    if (isDark){
        activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.songil_2)
        if (Build.VERSION.SDK_INT >= 31) {
            activity.window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            //activity.window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        } else {
            val windowInsetsController = ViewCompat.getWindowInsetsController(activity.window.decorView)
            windowInsetsController?.isAppearanceLightStatusBars = false
        }
    } else {
        if (Build.VERSION.SDK_INT >= 31) {
            //activity.window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            activity.window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        } else {
            val windowInsetsController = ViewCompat.getWindowInsetsController(activity.window.decorView)
            windowInsetsController?.isAppearanceLightStatusBars = true
        }
    }
}