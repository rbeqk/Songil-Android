package com.example.songil.page_login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.LoginFragment2Binding

class LoginFragment2(private val viewModel: LoginViewModel) : BaseFragment<LoginFragment2Binding>(LoginFragment2Binding::bind, R.layout.login_fragment_2) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.etAuthNumber.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkAuthNumberForm()
            }
        })

        binding.btnBack.setOnClickListener {
            (activity as LoginActivity).goToPhoneNumberFragment()
        }

    }
}