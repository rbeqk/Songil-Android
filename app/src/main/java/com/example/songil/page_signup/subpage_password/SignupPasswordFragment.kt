package com.example.songil.page_signup.subpage_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.config.SignUpProcess
import com.example.songil.databinding.SignupFragmentPasswordBinding
import com.example.songil.page_signup.SignupActivity
import com.example.songil.page_signup.models.SignUpInfo

class SignupPasswordFragment(private val signUpInfo: SignUpInfo) : BaseFragment<SignupFragmentPasswordBinding>(SignupFragmentPasswordBinding::bind, R.layout.signup_fragment_password) {

    private val viewModel : SignupPasswordViewModel by lazy {
        ViewModelProvider(this, SignupPasswordViewModel.SignupPasswordViewModelFactory(signUpInfo))[SignupPasswordViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setEditText()
        setButton()
        setObserver()
    }

    private fun setObserver(){
        val passwordFormResult = Observer<Boolean>{ liveData ->
            binding.cbAvailablePassword.isChecked = liveData
            if (liveData){
                binding.tvDescription.text = getString(R.string.available_password)
            }
            else {
                binding.tvDescription.text = getString(R.string.not_fit_password_rule)
            }
        }
        viewModel.btnActivate.observe(viewLifecycleOwner, passwordFormResult)
    }

    private fun setEditText(){
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {viewModel.checkPasswordForm()}
        })
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            (activity as SignupActivity).changeFragment(SignUpProcess.AUTH_CODE)
        }

        binding.btnNext.setOnClickListener {
            viewModel.setPassword()
            (activity as SignupActivity).changeFragment(SignUpProcess.PASSWORD_CONFIRM)
        }
    }

    fun onShow(){
        binding.etPassword.requestFocus()
    }
}