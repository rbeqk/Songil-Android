package com.example.songil.page_login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.LoginFragment1Binding

class LoginFragment1() : BaseFragment<LoginFragment1Binding>(LoginFragment1Binding::bind, R.layout.login_fragment_1) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()

        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkPhoneNumberForm()
            }
        })

        binding.btnAuth.setOnClickListener {
            viewModel.trySetAuthNumber()
        }
        binding.btnBack.setOnClickListener {
            (activity as LoginActivity).finishWithResult(false)
        }
    }
}