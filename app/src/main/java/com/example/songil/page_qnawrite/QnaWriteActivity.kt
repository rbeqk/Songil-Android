package com.example.songil.page_qnawrite

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.config.WriteType
import com.example.songil.databinding.QnaActivityWriteBinding
import com.example.songil.page_qnawrite.models.BodyQnaWrite

class QnaWriteActivity : BaseActivity<QnaActivityWriteBinding>(R.layout.qna_activity_write) {
    private val viewModel : QnaWriteViewModel by lazy { ViewModelProvider(this)[QnaWriteViewModel::class.java] }
    private lateinit var isNew : WriteType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setButton()
        setObserver()
        setEditText()

        isNew = (intent.getSerializableExtra(GlobalApplication.WRITE_TYPE) as WriteType)
        viewModel.qnaIdx = intent.getIntExtra(GlobalApplication.TARGET_IDX, -1)
        if (isNew == WriteType.MODIFY && viewModel.qnaIdx != -1){
            viewModel.tryGetQna()
        }
    }

    private fun setEditText(){
        binding.etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkActivate()
            }
        })

        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.checkActivate()
            }
        })
    }

    private fun setObserver(){
        val qnaWriteResultObserver = Observer<Boolean>{ liveData ->
            viewModel.checkActivate()
            if (liveData){
                val intent = Intent(this, BaseActivity::class.java)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                showSimpleToastMessage("Qna 업로드에 실패했습니다.\n잠시 후에 다시 시도해주세요.")
            }
        }
        viewModel.writeQnaResult.observe(this, qnaWriteResultObserver)

        val loadQnaResultObserver = Observer<Boolean>{ liveData ->
            if (liveData)
                binding.invalidateAll()
        }
        viewModel.loadQnaResult.observe(this, loadQnaResultObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.disableWriteBtn()
            if (isNew == WriteType.NEW){
                viewModel.tryWriteQna()
            } else {
                viewModel.tryModifyQna()
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}