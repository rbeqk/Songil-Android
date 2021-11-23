package com.example.songil.page_signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SignupFragment2Binding

class SignupFragment2() : BaseFragment<SignupFragment2Binding>(SignupFragment2Binding::bind, R.layout.signup_fragment_2) {

    private lateinit var viewModel: SignupViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SignupViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()
        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkPhoneNumberForm()
            }
        })

        binding.btnBack.setOnClickListener {
            viewModel.setFragmentIdx(-1)
        }

        setObserver()
    }

    private fun setObserver(){
        val checkDupPhoneObserver = Observer<Int>{ liveData ->
            when (liveData) {
                200 -> {
                    viewModel.setFragmentIdx(1)
                }
                else -> {}
            }
        }
        viewModel.phoneDupResult.observe(requireActivity(), checkDupPhoneObserver)
    }
}