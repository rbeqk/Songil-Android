package com.example.songil.page_login.sugpage_email

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.config.LoginProcess
import com.example.songil.databinding.LoginFragmentEmailBinding
import com.example.songil.page_login.LoginActivity
import com.example.songil.page_login.models.LoginInfo

class LoginEmailFragment(private val loginInfo : LoginInfo) : BaseFragment<LoginFragmentEmailBinding>(LoginFragmentEmailBinding::bind, R.layout.login_fragment_email) {

    private val viewModel : LoginEmailViewModel by lazy { ViewModelProvider(this, LoginEmailViewModel.LoginEmailViewModelFactory(loginInfo))[LoginEmailViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setButton()
        setEditText()
    }

    private fun setEditText(){
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.changeBtnState()
            }
        })
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            (activity as LoginActivity).changeFragment(LoginProcess.CANCEL)
        }

        binding.btnNext.setOnClickListener {
            viewModel.setEmail()
            (activity as LoginActivity).changeFragment(LoginProcess.PASSWORD)
        }
    }

    fun onShow(){
        binding.etEmail.requestFocus()
    }
}