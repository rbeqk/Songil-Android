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

        binding.btnCancel.setOnClickListener {
            viewModel.setFragmentIdx(-1)
        }

        setObserver()
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
}