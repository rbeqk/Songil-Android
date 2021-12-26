package com.example.songil.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.songil.R

fun setStatusBarBlack(activity : Activity, isDark : Boolean){
    val window = activity.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    if (isDark){
        window.statusBarColor = ContextCompat.getColor(activity, R.color.songil_2)
        when {
            Build.VERSION.SDK_INT >= 31 -> {
                window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            }
            Build.VERSION.SDK_INT >= 26 -> {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            }
            else -> {
                window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
    } else {
        //window.statusBarColor = ContextCompat.getColor(activity, R.color.songil_1)
        when {
            Build.VERSION.SDK_INT >= 31 -> {
                window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            }
            Build.VERSION.SDK_INT >= 26 -> {
                /*val windowInsetsController = ViewCompat.getWindowInsetsController(activity.window.decorView)
            windowInsetsController?.isAppearanceLightStatusBars = false*/
                //window.decorView.systemUiVisibility = 0
                //window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                window.decorView.setSystemUiVisibility(window.decorView.getSystemUiVisibility() and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                window.statusBarColor = ContextCompat.getColor(activity, R.color.songil_1)
            }
            else -> {
                window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            }
        }
    }
}