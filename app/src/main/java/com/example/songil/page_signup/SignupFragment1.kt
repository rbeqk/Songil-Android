package com.example.songil.page_signup

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SignupFragment1Binding
import com.example.songil.popup_term.TermBottomSheet

class SignupFragment1() : BaseFragment<SignupFragment1Binding>(SignupFragment1Binding::bind, R.layout.signup_fragment_1) {
    private lateinit var viewModel: SignupViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[SignupViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()

        setButton()
        setObserver()
    }

    private fun setButton(){
        binding.btnCancel.setOnClickListener {
            viewModel.setFragmentIdx(-1)
        }

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

        binding.tvTermsOptionalAd.setOnClickListener {
            val inputStream = resources.assets.open("terms_ad.txt")
            val size = inputStream.available()
            val buf = ByteArray(size)
            inputStream.read(buf)
            callTermDialog(String(buf))
        }
    }

    private fun setObserver(){
        val agreementDetailObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    val dialogFragment = TermBottomSheet(viewModel.agreementContent)
                    dialogFragment.show(childFragmentManager, dialogFragment.tag)
                }
                else -> {

                }
            }
        }
        viewModel.loadAgreementResult.observe(requireActivity(), agreementDetailObserver)
    }

    private fun callTermDialog(terms : String){
        val dialogFragment = TermBottomSheet(terms)
        dialogFragment.show(childFragmentManager, dialogFragment.tag)
    }
}