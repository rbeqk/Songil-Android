package com.example.songil.page_needlogin

import android.content.Intent
import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.NeedloginActivityBinding
import com.example.songil.page_login.LoginActivity
import com.example.songil.page_main.MainActivity
import com.example.songil.page_signup.SignupActivity

class NeedLoginActivity : BaseActivity<NeedloginActivityBinding>(R.layout.needlogin_activity) {

    private var isFirst = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isFirst = intent.getBooleanExtra("isFirst", false)

        // 테스트용!!!
        binding.ivLogo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


        binding.view = this
    }

    override fun onRestart() {
        super.onRestart()

        if (GlobalApplication.globalSharedPreferences.contains(GlobalApplication.X_ACCESS_TOKEN)){
            if (isFirst){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                finish()
            }
        }
    }

    fun goToBack(){
        finish()
    }

    fun goToLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun goToSignUp(){
        startActivity(Intent(this, SignupActivity::class.java))
    }
}