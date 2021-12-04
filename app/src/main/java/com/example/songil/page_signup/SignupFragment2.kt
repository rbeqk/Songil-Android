package com.example.songil.page_signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SignupFragment2Binding
import com.example.songil.popup_warning.PhoneDupDialog

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
                    clearResponseMessage()
                    viewModel.setFragmentIdx(1)
                }
                3200 -> {
                    val dialogFragment = PhoneDupDialog()
                    dialogFragment.show(childFragmentManager, dialogFragment.tag)
                }
                else -> {
                    binding.tvResponseMessage.text = viewModel.apiMessage
                    binding.tvResponseMessage.setTextColor(ContextCompat.getColor(activity as SignupActivity, R.color.tomato))
                }
            }
        }
        viewModel.phoneDupResult.observe(requireActivity(), checkDupPhoneObserver)
    }

    private fun clearResponseMessage(){
        binding.tvResponseMessage.text = getString(R.string.input_phoneNumber_without_hyphen)
        binding.tvResponseMessage.setTextColor(ContextCompat.getColor(activity as SignupActivity, R.color.g_4))
    }
}