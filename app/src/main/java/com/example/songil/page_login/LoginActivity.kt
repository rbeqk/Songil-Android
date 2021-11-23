package com.example.songil.page_login

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.LoginActivityBinding

class LoginActivity : BaseActivity<LoginActivityBinding>(R.layout.login_activity) {

    private lateinit var viewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, LoginFragment1()).commit()

        setObserver()
    }

    private fun goToAuthFragment() {
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, LoginFragment2()).commit()
    }

    fun backToPhoneNumberFragment() {
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, LoginFragment1()).commit()
    }

    fun finishWithResult(isLogin : Boolean = false){
        // 로그인 성공시 추가적인 작업이 있을 수 있다.
        finish()
    }

    private fun setObserver(){
        val loginResultObserver = Observer<Int> { liveData ->
            when (liveData){
                200 -> {
                    finishWithResult(true)
                }
                else -> {

                }
            }
        }
        viewModel.loginResultCode.observe(this, loginResultObserver)

        val authResultCode = Observer<Int> { liveData ->
            when(liveData){
                200 -> {
                    goToAuthFragment()
                }
                else -> {

                }
            }
        }
        viewModel.authResultCode.observe(this, authResultCode)
    }
}