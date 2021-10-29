package com.example.songil.page_login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.LoginFragment1Binding

class LoginFragment1(private val viewModel: LoginViewModel) : BaseFragment<LoginFragment1Binding>(LoginFragment1Binding::bind, R.layout.login_fragment_1) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkPhoneNumberForm()
            }
        })

        binding.btnAuth.setOnClickListener {
            viewModel.sendPhoneNumber { (activity as LoginActivity).goToAuthFragment() }
        }
    }
}