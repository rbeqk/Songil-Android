package com.example.songil.page_signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SignupFragment3Binding
import kotlinx.coroutines.*

class SignupFragment3() : BaseFragment<SignupFragment3Binding>(SignupFragment3Binding::bind, R.layout.signup_fragment_3) {

    private lateinit var viewModel : SignupViewModel
    private lateinit var timer : Job
    private var timerCount = 0

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

        setButton()
        setObserver()

    }

    fun fragmentShow(){
        viewModel.tryGetAuthNumber()
        binding.etAuthNumber.requestFocus()
    }

    override fun onDestroyView() {
        stopTimer()
        super.onDestroyView()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            stopTimer()
            viewModel.setFragmentIdx(-1)
        }

        binding.btnNext.setOnClickListener {
            viewModel.tryCheckAuthNumber()
        }
        binding.btnReceiveAgain.setOnClickListener {
            viewModel.tryGetAuthNumber()
        }
    }


    private fun setObserver(){
        val checkAuthObserver = Observer<Int> { liveData ->
            when(liveData){
                200 -> {
                    stopTimer()
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
                    startTimer()
                }
            }
        }
        viewModel.getAuthResult.observe(requireActivity(), setAuthObserver)

    }

    private fun startTimer(){
        if (::timer.isInitialized) timer.cancel()

        timerCount = 180
        timer = CoroutineScope(Dispatchers.Main).launch {
            while(timerCount > 0){
                timerCount -= 1
                binding.tvRemainTime.text = getString(R.string.remain_time_with_time, timerCount / 60, timerCount % 60)
                delay(1000L)
            }
        }
    }

    fun stopTimer(){
        if (::timer.isInitialized) timer.cancel()
    }
}