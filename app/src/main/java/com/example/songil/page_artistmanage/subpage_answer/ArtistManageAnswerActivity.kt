package com.example.songil.page_artistmanage.subpage_answer

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.InquiryActivityAnswerBinding

class ArtistManageAnswerActivity : BaseActivity<InquiryActivityAnswerBinding>(R.layout.inquiry_activity_answer){

    private val viewModel : ArtistManageAnswerViewModel by lazy { ViewModelProvider(this)[ArtistManageAnswerViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setEditText()
        viewModel.tempGetInquiry()

        binding.btnAnswer.setOnClickListener {
            val intent = Intent(this, BaseActivity::class.java)
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    private fun setEditText(){
        binding.etAnswer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkActivate()
            }

        })
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}