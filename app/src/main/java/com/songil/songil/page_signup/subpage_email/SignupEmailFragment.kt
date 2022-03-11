package com.songil.songil.page_signup.subpage_email

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.songil.songil.R
import com.songil.songil.config.BaseFragment
import com.songil.songil.config.SignUpProcess
import com.songil.songil.databinding.SignupFragmentEmailBinding
import com.songil.songil.page_signup.SignupActivity
import com.songil.songil.page_signup.models.SignUpInfo
import com.songil.songil.popup_warning.EmailDupDialog

class SignupEmailFragment(private val signUpInfo: SignUpInfo) : BaseFragment<SignupFragmentEmailBinding>(SignupFragmentEmailBinding::bind, R.layout.signup_fragment_email) {
    private val viewModel : SignupEmailViewModel by lazy {
        ViewModelProvider(this, SignupEmailViewModel.SignupEmailViewModelFactory(signUpInfo))[SignupEmailViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setButton()
        setEditText()
        setObserver()
    }

    private fun setObserver(){
        val issueAuthCodeObserver = Observer<Int>{ liveData ->
            viewModel.checkEmailForm()
            when (liveData) {
                200 -> {
                    viewModel.setRequestEmail()
                    (activity as SignupActivity).changeFragment(SignUpProcess.AUTH_CODE)
                }
                3200 -> {
                    val dialogFragment = EmailDupDialog()
                    dialogFragment.show(childFragmentManager, dialogFragment.tag)
                }
            }
        }
        viewModel.issueAuthCodeResult.observe(viewLifecycleOwner, issueAuthCodeObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            (activity as SignupActivity).changeFragment(SignUpProcess.TERM)
            val imm = (activity as SignupActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etEmail.windowToken, 0)
        }

        binding.btnNext.setOnClickListener {
            binding.etEmail.clearFocus()
            viewModel.btnActivate.value = false
            viewModel.tryIssueAuthCode()
        }
    }

    private fun setEditText(){
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {viewModel.checkEmailForm()}
        })
    }

    fun onShow(){
        binding.etEmail.requestFocus()
    }
}