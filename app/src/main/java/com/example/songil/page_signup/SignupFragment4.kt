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
import com.example.songil.databinding.SignupFragment4Binding

class SignupFragment4() : BaseFragment<SignupFragment4Binding>(SignupFragment4Binding::bind, R.layout.signup_fragment_4) {

    private lateinit var viewModel : SignupViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SignupViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()

        binding.etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkNickNameForm()
            }
        })

        setObserver()
    }

    private fun setObserver(){
        val checkNicknameObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    viewModel.trySignUp()
                }
                else -> {
                    binding.tvDescription.text = viewModel.apiMessage
                }
            }
        }
        viewModel.nicknameDupResult.observe(requireActivity(), checkNicknameObserver)

        val signUpObserver = Observer<Int> { liveData ->
            when(liveData){
                200 -> {
                    viewModel.setFragmentIdx(4)
                }
                else -> {

                }
            }
        }
        viewModel.signUpResult.observe(requireActivity(), signUpObserver)
    }
}