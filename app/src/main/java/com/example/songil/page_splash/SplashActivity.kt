package com.example.songil.page_splash

import android.content.Intent
import com.example.songil.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.SplashActivityBinding
import com.example.songil.page_login.LoginActivity

class SplashActivity : BaseActivity<SplashActivityBinding>(R.layout.splash_activity){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (GlobalApplication.globalSharedPreferences.getString("test", null) == null) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 1500)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 1500)
        }
    }
}