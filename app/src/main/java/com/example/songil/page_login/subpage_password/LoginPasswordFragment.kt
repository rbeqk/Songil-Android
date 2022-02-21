package com.example.songil.page_login.subpage_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.config.LoginProcess
import com.example.songil.databinding.LoginFragmentPasswordBinding
import com.example.songil.page_login.LoginActivity
import com.example.songil.page_login.models.LoginInfo

class LoginPasswordFragment(private val loginInfo : LoginInfo) : BaseFragment<LoginFragmentPasswordBinding>(LoginFragmentPasswordBinding::bind, R.layout.login_fragment_password) {

    private val viewModel : LoginPasswordViewModel by lazy { ViewModelProvider(this, LoginPasswordViewModel.LoginPasswordViewModelFactory(loginInfo))[LoginPasswordViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setButton()
        setObserver()
        setEditText()
    }

    fun onShow(){
        binding.etPassword.requestFocus()
    }

    private fun setEditText(){
        binding.etPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.changeBtnState()
            }
        })
    }

    private fun setObserver(){
        val loginResultObserver = Observer<Int>{ liveData ->
            viewModel.changeBtnState()
            when (liveData){
                200 ->{
                    viewModel.tryGetUserType()
                    //(activity as LoginActivity).changeFragment(LoginProcess.COMPLETE)
                }
                else -> {
                    binding.tvDescription.text = getString(R.string.cannot_find_user_login)
                }
            }
        }
        viewModel.loginResult.observe(viewLifecycleOwner, loginResultObserver)

        val getUserTypeObserver =Observer<Int> { _ ->
            (activity as LoginActivity).changeFragment(LoginProcess.COMPLETE)
        }
        viewModel.userTypeResultCode.observe(viewLifecycleOwner, getUserTypeObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            binding.tvDescription.text = ""
            (activity as LoginActivity).changeFragment(LoginProcess.EMAIL)
        }

        binding.btnNext.setOnClickListener {
            binding.tvDescription.text = ""
            viewModel.btnActivate.value = false
            viewModel.setPassword()
            viewModel.tryLogin()
        }
    }
}