package com.songil.songil.page_signup.subpage_authcode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.songil.songil.R
import com.songil.songil.config.BaseFragment
import com.songil.songil.config.SignUpProcess
import com.songil.songil.databinding.SignupFragmentAuthcodeBinding
import com.songil.songil.page_signup.SignupActivity
import com.songil.songil.page_signup.models.SignUpInfo
import com.songil.songil.popup_warning.EmailDupDialog
import kotlinx.coroutines.*

class SignupAuthcodeFragment(private val signUpInfo: SignUpInfo) : BaseFragment<SignupFragmentAuthcodeBinding>(SignupFragmentAuthcodeBinding::bind, R.layout.signup_fragment_authcode) {

    private val viewModel : SignupAuthcodeViewModel by lazy { ViewModelProvider(this, SignupAuthcodeViewModel.SignupAuthcodeViewModelFactory(signUpInfo))[SignupAuthcodeViewModel::class.java] }
    private lateinit var timer : Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setButton()
        setObserver()
        setEditText()

        initTimer()
    }

    private fun setEditText(){
        binding.etAuthCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) { viewModel.checkAuthButtonStatus() }
        })
    }

    private fun setObserver(){
        val checkAuthCodeObserver = Observer<Int>{ liveData ->
            viewModel.checkAuthButtonStatus()
            when (liveData) {
                200 -> { // 성공
                    moveToNext()
                }
                2100 -> { // 전 페이지에서 입력한 이메일이 혹시나 형식에 맞지 않을 경우
                    binding.tvResponseMessage.text = "올바르지 않은 이메일 형식입니다."
                }
                3102 -> { // 유효하지 않은 인증번호
                    binding.tvResponseMessage.text = "유효하지 않은 인증번호입니다."
                }
            }
        }
        viewModel.checkAuthCodeResult.observe(viewLifecycleOwner, checkAuthCodeObserver)

        val issueAuthCodeObserver = Observer<Int>{ liveData ->
            binding.btnReceiveAgain.setBackgroundResource(R.drawable.shape_text_button_black)
            binding.btnReceiveAgain.isClickable = true
            viewModel.checkAuthButtonStatus()
            when (liveData){
                200 -> {
                    initTimer()
                }
                3200 -> {
                    val dialogFragment = EmailDupDialog()
                    dialogFragment.show(childFragmentManager, dialogFragment.tag)
                }
            }
        }
        viewModel.issueAuthCodeResult.observe(viewLifecycleOwner, issueAuthCodeObserver)

        val timerCountObserver = Observer<Int>{ liveData ->
            viewModel.checkAuthButtonStatus()
            binding.tvRemainTime.text = getString(R.string.remain_time_with_time, liveData / 60, liveData % 60)
        }
        viewModel.timerCount.observe(viewLifecycleOwner, timerCountObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            (activity as SignupActivity).changeFragment(SignUpProcess.EMAIL)
            binding.tvResponseMessage.text = ""
        }

        binding.btnNext.setOnClickListener {
            viewModel.btnActivate.value = false
            viewModel.tryCheckAuthCode()
            viewModel.btnActivate.value = false
            binding.tvResponseMessage.text = ""
        }

        binding.btnReceiveAgain.setOnClickListener {
            binding.btnReceiveAgain.setBackgroundResource(R.drawable.shape_rectangle_g1_radius_4)
            binding.btnReceiveAgain.isClickable = false
            viewModel.tryIssueAuthCodeAgain()
            binding.tvResponseMessage.text = ""
        }
    }

    private fun initTimer(){
        if (::timer.isInitialized) timer.cancel()

        viewModel.timerCount.value = 180
        timer = lifecycleScope.launch{
            while (viewModel.timerCount.value!! > 0){
                viewModel.timerCount.value = viewModel.timerCount.value!!.minus(1)

                delay(1000L)
            }

        }
    }

    private fun stopTimer(){
        if (::timer.isInitialized) timer.cancel()
        binding.tvRemainTime.text = ""
    }

    private fun moveToNext(){
        stopTimer()
        viewModel.setConfirmEmail()
        (activity as SignupActivity).changeFragment(SignUpProcess.PASSWORD)
    }

    fun onShow(){
        binding.etAuthCode.requestFocus()
    }
}