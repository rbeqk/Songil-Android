package com.example.songil.page_signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SignupFragment3Binding

class SignupFragment3() : BaseFragment<SignupFragment3Binding>(SignupFragment3Binding::bind, R.layout.signup_fragment_3) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[SignupViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()
        binding.etAuthNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkAuthNumberForm()
            }
        })

        binding.btnNext.setOnClickListener {
            if (viewModel.compareAuthNumber()){
                viewModel.setFragmentIdx(1)
            } else {
                Log.d("authNumber", "입력한 인증번호가 틀립니다.")
            }
        }
        binding.btnReceiveAgain.setOnClickListener {
            viewModel.tryGetAuthNumber()
        }

        val authNumberObserver = Observer<String>{ liveData ->
            Log.d("authNumber", liveData)
        }
        viewModel.authNumber.observe(requireActivity(), authNumberObserver)

        viewModel.tryGetAuthNumber()
    }
}