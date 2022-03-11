package com.songil.songil.page_needlogin

import android.content.Intent
import android.os.Bundle
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.databinding.NeedloginActivityBinding
import com.songil.songil.page_login.LoginActivity
import com.songil.songil.page_main.MainActivity
import com.songil.songil.page_signup.SignupActivity
import com.songil.songil.utils.setStatusBarBlack

class NeedLoginActivity : BaseActivity<NeedloginActivityBinding>(R.layout.needlogin_activity) {

    private var isFirst = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarBlack(this, true)

        isFirst = intent.getBooleanExtra("isFirst", false)

        // 테스트용!!!
        binding.ivLogo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        binding.view = this
    }

    override fun onRestart() {
        super.onRestart()

        if (GlobalApplication.globalSharedPreferences.contains(GlobalApplication.X_ACCESS_TOKEN)){
            if (isFirst){
                startActivity(Intent(this, MainActivity::class.java))
                onBackPressed()
            } else {
                onBackPressed()
            }
        }
    }

    fun goToBack(){
        onBackPressed()
        //finish()
    }

    fun goToLogin(){
        startActivityHorizontal(Intent(this, LoginActivity::class.java))
        //startActivity(Intent(this, LoginActivity::class.java))
    }

    fun goToSignUp(){
        startActivityHorizontal(Intent(this, SignupActivity::class.java))
        //startActivity(Intent(this, SignupActivity::class.java))
    }

    override fun finish() {
        super.finish()
        exitVertical
    }
}