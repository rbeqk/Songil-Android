package com.example.songil.page_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.LoginActivityBinding

class LoginActivity : BaseActivity<LoginActivityBinding>(R.layout.login_activity) {

    private val fragmentList = ArrayList<Fragment>()
    private lateinit var viewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        fragmentList.add(LoginFragment1(viewModel))

        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, fragmentList[viewModel.fragmentIdx.value!!]).commit()
    }
}