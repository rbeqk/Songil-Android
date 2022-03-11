package com.songil.songil.page_signup.subpage_nickname

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
import com.songil.songil.databinding.SignupFragmentNicknameBinding
import com.songil.songil.page_signup.SignupActivity
import com.songil.songil.page_signup.models.SignUpInfo
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignupNicknameFragment(private val signUpInfo: SignUpInfo) : BaseFragment<SignupFragmentNicknameBinding>(SignupFragmentNicknameBinding::bind, R.layout.signup_fragment_nickname) {

    private val viewModel : SignupNicknameViewModel by lazy { ViewModelProvider(this, SignupNicknameViewModel.SignupNicknameViewModelFactory(signUpInfo))[SignupNicknameViewModel::class.java] }
    private var debounceJob : Job ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setEditText()
        setObserver()
        setButton()
    }

    private fun setButton(){
        binding.btnNext.setOnClickListener {
            viewModel.btnActivate.value = false
            binding.etNickname.isEnabled = false
            viewModel.trySignUp()
        }

        binding.btnBack.setOnClickListener {
            (activity as SignupActivity).changeFragment(SignUpProcess.PASSWORD_CONFIRM)
        }
    }

    private fun setObserver(){
        val nicknameCheckObserver = Observer<Int>{ liveData ->
            binding.cbAvailableNickname.isChecked = (liveData == 200)
            viewModel.btnActivate.value = (liveData == 200)
            when (liveData){
                200 -> { binding.tvDescription.text = getString(R.string.available_nickname) }
                2100 -> { binding.tvDescription.text = "" }
                2367 -> { binding.tvDescription.text = getString(R.string.unavailable_nickname_word_cnt) }
                3201 -> { binding.tvDescription.text = getString(R.string.unavailable_nickname_duplicate) }
                -2 -> { binding.tvDescription.text = getString(R.string.unavailable_nickname_word_form) }
            }
        }
        viewModel.nicknameCheckResult.observe(viewLifecycleOwner, nicknameCheckObserver)

        val signupResultObserver = Observer<Boolean>{ liveData ->
            viewModel.btnActivate.value = true
            binding.etNickname.isEnabled = true
            if (liveData){
                (activity as SignupActivity).changeFragment(SignUpProcess.COMPLETE)
                (activity as SignupActivity).showSimpleToastMessage(getString(R.string.toast_signup_success))
            } else {
                (activity as SignupActivity).showSimpleToastMessage(getString(R.string.toast_signup_failure))
            }
        }
        viewModel.signupResult.observe(viewLifecycleOwner, signupResultObserver)
    }

    private fun setEditText(){
        binding.etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.btnActivate.value = false
                val text = s.toString().replace(" ", "")

                debounceJob?.cancel()
                debounceJob = lifecycleScope.launch {
                    delay(300L)
                    viewModel.tryNicknameDupCheck(text)
                }

            }
        })
    }

    fun onShow(){
        binding.etNickname.requestFocus()
    }
}