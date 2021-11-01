package com.example.songil.page_signup

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SignupFragment4Binding

class SignupFragment4() : BaseFragment<SignupFragment4Binding>(SignupFragment4Binding::bind, R.layout.signup_fragment_4) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[SignupViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()
    }
}