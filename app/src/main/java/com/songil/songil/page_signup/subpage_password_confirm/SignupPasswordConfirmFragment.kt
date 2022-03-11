package com.songil.songil.page_signup.subpage_password_confirm

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.songil.songil.R
import com.songil.songil.config.BaseFragment
import com.songil.songil.config.SignUpProcess
import com.songil.songil.databinding.SignupFragmentPasswordConfirmBinding
import com.songil.songil.page_signup.SignupActivity
import com.songil.songil.page_signup.models.SignUpInfo

class SignupPasswordConfirmFragment(private val signUpInfo: SignUpInfo) :
    BaseFragment<SignupFragmentPasswordConfirmBinding>(SignupFragmentPasswordConfirmBinding::bind, R.layout.signup_fragment_password_confirm) {

    private val viewModel : SignupPasswordConfirmViewModel by lazy {
        ViewModelProvider(this, SignupPasswordConfirmViewModel.SignupPasswordConfirmViewModelFactory(signUpInfo))[SignupPasswordConfirmViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setEditText()
        setButton()
    }

    private fun setEditText(){
        binding.etPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {viewModel.checkPasswordForm()}
        })
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            (activity as SignupActivity).changeFragment(SignUpProcess.PASSWORD)
        }

        binding.btnNext.setOnClickListener {
            if (viewModel.confirmPassword()) {
                (activity as SignupActivity).changeFragment(SignUpProcess.NICKNAME)
            } else {
                binding.tvDescription.text = getString(R.string.not_match_password)
            }
        }
    }

    fun onShow(){
        binding.etPassword.requestFocus()
    }
}