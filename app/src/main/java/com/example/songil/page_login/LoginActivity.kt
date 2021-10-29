package com.example.songil.page_login

import android.os.Bundle
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

        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, LoginFragment1(viewModel)).commit()
    }

    fun goToAuthFragment() {
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, LoginFragment2(viewModel)).commit()
    }

    fun goToPhoneNumberFragment() {
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, LoginFragment1(viewModel)).commit()
    }
}