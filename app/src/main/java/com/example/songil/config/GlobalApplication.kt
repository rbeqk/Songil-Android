package com.example.songil.config

import android.app.Application
import android.content.SharedPreferences

class GlobalApplication : Application() {
    companion object {
        val API_URL = ""
        lateinit var globalSharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        globalSharedPreferences = applicationContext.getSharedPreferences("Songil", MODE_PRIVATE)
    }
}