package com.example.songil.page_signup.subpage_term

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.config.SignUpProcess
import com.example.songil.databinding.SignupFragmentTermBinding
import com.example.songil.page_signup.SignupActivity
import com.example.songil.page_signup.models.SignUpInfo
import com.example.songil.popup_term.TermBottomSheet

class SignupTermFragment(private val signUpInfo: SignUpInfo) : BaseFragment<SignupFragmentTermBinding>(SignupFragmentTermBinding::bind, R.layout.signup_fragment_term) {

    private val viewModel : SignupTermViewModel by lazy { ViewModelProvider(this, SignupTermViewModel.SignupTermViewModelFactory(signUpInfo))[SignupTermViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setButton()
    }

    private fun setButton(){
        binding.tvTermsRequiredUsage.setOnClickListener {
            val inputStream = resources.assets.open("terms_usage.txt")
            val size = inputStream.available()
            val buf = ByteArray(size)
            inputStream.read(buf)
            callTermDialog(String(buf))
        }

        binding.tvTermsRequiredPrivacyPolicy.setOnClickListener {
            val inputStream = resources.assets.open("terms_privacy_policy.txt")
            val size = inputStream.available()
            val buf = ByteArray(size)
            inputStream.read(buf)
            callTermDialog(String(buf))
        }

        binding.btnNext.setOnClickListener {
            viewModel.setAgreeState()
            (activity as SignupActivity).changeFragment(SignUpProcess.EMAIL)
        }

        binding.btnCancel.setOnClickListener {
            (activity as SignupActivity).changeFragment(SignUpProcess.CANCEL)
        }
    }

    private fun callTermDialog(terms : String){
        val dialogFragment = TermBottomSheet(terms)
        dialogFragment.show(childFragmentManager, dialogFragment.tag)
    }
}