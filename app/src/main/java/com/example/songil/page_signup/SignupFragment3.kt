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

    private lateinit var viewModel : SignupViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SignupViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()
        binding.etAuthNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkAuthNumberForm()
            }
        })

        binding.btnBack.setOnClickListener {
            viewModel.setFragmentIdx(-1)
        }

        binding.btnNext.setOnClickListener {
            viewModel.tryCheckAuthNumber()
        }
        binding.btnReceiveAgain.setOnClickListener {
            viewModel.tryGetAuthNumber()
        }

        setObserver()

        viewModel.tryGetAuthNumber()
    }

    private fun setObserver(){
        val checkAuthObserver = Observer<Int> { liveData ->
            when(liveData){
                200 -> {
                    viewModel.setFragmentIdx(1)
                }
                else -> {
                    binding.tvRemainTime.text = viewModel.apiMessage
                    binding.tvRemainTime.setTextColor(requireContext().getColor(R.color.songil_2))
                }
            }
        }
        viewModel.checkAuthResult.observe(requireActivity(), checkAuthObserver)

        val setAuthObserver = Observer<Int>{ liveData ->
            when(liveData){
                200 -> {
                    viewModel.startTimer()
                }
            }
        }
        viewModel.getAuthResult.observe(requireActivity(), setAuthObserver)

        val timeObserver = Observer<Int> { liveData ->
            binding.tvRemainTime.text = getString(R.string.remain_time_with_time, liveData / 60, liveData % 60)
        }
        viewModel.authTimer.observe(requireActivity(), timeObserver)
    }
}