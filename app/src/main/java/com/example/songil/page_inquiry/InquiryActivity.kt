package com.example.songil.page_inquiry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseViewModel
import com.example.songil.config.GlobalApplication
import com.example.songil.config.InquiryTarget
import com.example.songil.databinding.InquiryActivityBinding
import com.example.songil.popup_inquiry.InquiryDialog

class InquiryActivity : BaseActivity<InquiryActivityBinding>(R.layout.inquiry_activity) {
    private lateinit var viewModel : InquiryViewModel
    private var craftIdx = 0
    private lateinit var targetType : InquiryTarget

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        craftIdx = intent.getIntExtra(GlobalApplication.TARGET_IDX, 0)
        targetType = intent.getSerializableExtra(GlobalApplication.TARGET_IDX_TYPE) as InquiryTarget

        viewModel = ViewModelProvider(this, InquiryViewModel.InquiryViewModelFactory(craftIdx, targetType))[InquiryViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setButton()
        setObserver()

        binding.etContent.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.changeTextCntTextView()
                viewModel.checkTextSizeInRange()
            }
        })
    }

    private fun setObserver(){
        val inquiryResultObserver = Observer<Boolean>{ liveData ->
            viewModel.checkTextSizeInRange()
            if (liveData) {
                val dialogFragment = InquiryDialog()
                dialogFragment.show(supportFragmentManager, dialogFragment.tag)
            } else {
                showSimpleToastMessage("네트워크 에러, 잠시 후에 실행해주세요")
            }
        }
        viewModel.inquiryResult.observe(this, inquiryResultObserver)

        val errorObserver = Observer<BaseViewModel.FetchState> {
            viewModel.checkTextSizeInRange()
            val errorString = "에러 발생 : " + when(it){
                BaseViewModel.FetchState.BAD_INTERNET -> { "BAD_INTERNET" }
                BaseViewModel.FetchState.FAIL -> { "FAIL"}
                BaseViewModel.FetchState.WRONG_CONNECTION -> { "WRONG_CONNECTION"}
                BaseViewModel.FetchState.PARSE_ERROR -> { "PARSE_ERROR"}
                null -> {"UNKNOWN_ERROR"}
            }
            showSimpleToastMessage(errorString)
        }
        viewModel.fetchState.observe(this, errorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnRegister.setOnClickListener {
            viewModel.buttonActivate.value = false
            viewModel.trySendInquiry()
        }
    }
}