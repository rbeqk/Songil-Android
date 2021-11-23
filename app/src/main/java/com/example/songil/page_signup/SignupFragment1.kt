package com.example.songil.page_signup

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SignupFragment1Binding

class SignupFragment1() : BaseFragment<SignupFragment1Binding>(SignupFragment1Binding::bind, R.layout.signup_fragment_1) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[SignupViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()

        binding.btnCancel.setOnClickListener {
            viewModel.setFragmentIdx(-1)
        }
    }
}