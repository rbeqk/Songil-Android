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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // 테스트용!!!
        binding.ivLogo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


        binding.view = this
    }

    override fun onRestart() {
        super.onRestart()

        if (GlobalApplication.globalSharedPreferences.getInt(GlobalApplication.USER_IDX, 0) != 0){
            finish()
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