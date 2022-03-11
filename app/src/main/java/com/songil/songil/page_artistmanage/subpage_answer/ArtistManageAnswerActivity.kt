package com.songil.songil.page_artistmanage.subpage_answer

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.data.AskDetail
import com.songil.songil.databinding.InquiryActivityAnswerBinding

class ArtistManageAnswerActivity : BaseActivity<InquiryActivityAnswerBinding>(R.layout.inquiry_activity_answer){

    private val viewModel : ArtistManageAnswerViewModel by lazy {
        ViewModelProvider(this,
                ArtistManageAnswerViewModel.ArtistManageAnswerViewModelFactory(intent.getIntExtra(GlobalApplication.TARGET_IDX, 0), status))[ArtistManageAnswerViewModel::class.java]
    }
    private var status = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        status = intent.getIntExtra("ANSWER", 1)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        if (status == 2){
            binding.etAnswer.isEnabled = false
            binding.tvAnswer.text = getString(R.string.answer_content)
            binding.tvTitle.text = getString(R.string.answer_content)
        }

        setEditText()
        setButton()
        setObserver()

        viewModel.tryGetInquiry()
    }

    private fun setButton(){
        binding.btnAnswer.setOnClickListener {
            viewModel.trySendAnswer()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setObserver(){
        viewModel.fetchState.observe(this, baseNetworkErrorObserver)

        val inquiryDetailObserver = Observer<AskDetail> { liveData ->
            binding.tvCraftNickname.text = getString(R.string.form_craft_nickname, liveData.name, liveData.nickname)
            binding.invalidateAll()
        }
        viewModel.inquiryContent.observe(this, inquiryDetailObserver)

        val sendAnswerObserver = Observer<Boolean> { liveData ->
            if (liveData){
                val intent = Intent(this, BaseActivity::class.java)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                showSimpleToastMessage(getString(R.string.fail_to_send_answer))
            }
        }
        viewModel.sendAnswerResult.observe(this, sendAnswerObserver)
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