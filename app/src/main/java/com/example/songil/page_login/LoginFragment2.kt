package com.example.songil.page_login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.LoginFragment2Binding
import kotlinx.coroutines.*
import kotlin.concurrent.timer

class LoginFragment2() : BaseFragment<LoginFragment2Binding>(LoginFragment2Binding::bind, R.layout.login_fragment_2) {

    private lateinit var  viewModel : LoginViewModel
    private lateinit var timer : Job
    private var timerCount = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()

        binding.etAuthNumber.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkAuthNumberForm()
            }
        })

        binding.btnBack.setOnClickListener {
            stopTimer()
            (activity as LoginActivity).backToPhoneNumberFragment()
        }

        binding.btnReceiveAgain.setOnClickListener {
            stopTimer()
            viewModel.trySetAuthNumber()
        }

    }

    fun onShow(){
        binding.etAuthNumber.requestFocus()
    }

    fun startTimer() {
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