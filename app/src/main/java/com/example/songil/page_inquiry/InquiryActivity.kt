package com.example.songil.page_inquiry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.InquiryActivityBinding
import com.example.songil.popup_inquiry.InquiryDialog

class InquiryActivity : BaseActivity<InquiryActivityBinding>(R.layout.inquiry_activity) {
    private lateinit var viewModel : InquiryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[InquiryViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setButton()

        binding.etContent.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.changeTextLength()
            }
        })


    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnRegister.setOnClickListener {
            val dialogFragment = InquiryDialog()
            dialogFragment.show(supportFragmentManager, dialogFragment.tag)
        }
    }
}