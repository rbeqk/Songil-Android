package com.example.songil.page_splash

import android.content.Intent
import com.example.songil.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseViewModel
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.SplashActivityBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.popup_warning.SocketTimeoutDialog
import com.example.songil.utils.setStatusBarBlack
//import com.example.songil.page_needlogin.NeedLoginActivity

class SplashActivity : BaseActivity<SplashActivityBinding>(R.layout.splash_activity){

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarBlack(this, true)

        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        setObserver()

        logoAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            if (GlobalApplication.globalSharedPreferences.getBoolean(GlobalApplication.IS_FIRST_EXEC, true) &&
                GlobalApplication.globalSharedPreferences.getString(GlobalApplication.X_ACCESS_TOKEN, null) == null) {
                val edit = GlobalApplication.globalSharedPreferences.edit()
                edit.putBoolean(GlobalApplication.IS_FIRST_EXEC, false).apply()

                /*val intent = Intent(this, NeedLoginActivity::class.java)
                intent.putExtra("isFirst", true)
                startActivity(intent)*/
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                viewModel.tryAutoLogin()
            }
        }, 1000)
    }

    private fun setObserver(){
        val autoLoginObserver = Observer<Int> { liveData ->
            when (liveData){
                200 ->{
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else ->{
                    // 다시 시도 여부를 묻는 dialog 가 필요할 듯
                    // ????
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
        viewModel.autoLoginResultCode.observe(this, autoLoginObserver)

        val errorObserver = Observer<BaseViewModel.FetchState>{ _ ->
            val socketTimeoutDialog = SocketTimeoutDialog()
            socketTimeoutDialog.show(supportFragmentManager, socketTimeoutDialog.tag)
        }
        viewModel.fetchState.observe(this, errorObserver)
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