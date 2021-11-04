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

        val fragmentIdxObserver = Observer<Int>{ liveData ->
            when (liveData){
                -1 -> finish()
                0 -> goToPhoneNumberFragment()
                1 -> goToAuthFragment()
                else -> finish()
            }
        }
        viewModel.fragmentIdx.observe(this, fragmentIdxObserver)
    }

    private fun goToAuthFragment() {
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, LoginFragment2()).commit()
    }

    private fun goToPhoneNumberFragment() {
        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, LoginFragment1()).commit()
    }
}