package com.example.songil.page_splash

import android.content.Intent
import com.example.songil.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.SplashActivityBinding
import com.example.songil.page_main.MainActivity

class SplashActivity : BaseActivity<SplashActivityBinding>(R.layout.splash_activity){

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        setObserver()

        logoAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            if (GlobalApplication.globalSharedPreferences.getString(GlobalApplication.X_ACCESS_TOKEN, null) == null) {
                viewModel.requestAuthLogin()
            } else {
                viewModel.requestAuthJwt()
            }
        }, 1000)

        /*if (GlobalApplication.globalSharedPreferences.getString(GlobalApplication.X_ACCESS_TOKEN, null) == null) {
            viewModel.requestAuthLogin()
        } else {
            viewModel.requestAuthJwt()
        }*/
    }

    private fun setObserver(){
        val loginResultObserver = Observer<Int>{ liveData ->
            when (liveData){
                1000 -> {
                    /*startActivity(Intent(this, MainActivity::class.java))
                    finish()*/
                    viewModel.requestAuthJwt()
                }
                else -> {
                    Log.d("resultCode", liveData.toString())
                }
            }
        }
        viewModel.authLoginResultCode.observe(this, loginResultObserver)
        val jwtResultObserver = Observer<Int>{ liveData ->
            when(liveData){
                1001 -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else -> {
                    Log.d("resultCode", liveData.toString())
                }
            }
        }
        viewModel.authJwtResultCode.observe(this, jwtResultObserver)
    }

    private fun logoAnimation(){
        binding.apply {
            ivLogo1.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).setDuration(1000).setListener(null)
            }
        }
    }
}