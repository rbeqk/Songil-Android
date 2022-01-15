package com.example.songil.page_qna

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.ChatActivityBinding
import com.example.songil.recycler.adapter.PostAndChatAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class QnaActivity : BaseActivity<ChatActivityBinding>(R.layout.chat_activity) {

    private val viewModel : QnaViewModel by lazy { ViewModelProvider(this)[QnaViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.qnaIdx = intent.getIntExtra(GlobalApplication.QNA_IDX, 1)

        setRecyclerView()
        setObserver()
        setButton()

        viewModel.getQna()
    }

    private fun setRecyclerView(){
        binding.rvComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvComment.adapter = PostAndChatAdapter()
    }

    private fun setObserver(){
        val qnaResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    lifecycleScope.launch {
                        viewModel.flow.collectLatest { pagingData ->
                            (binding.rvComment.adapter as PostAndChatAdapter).submitData(pagingData)
                        }
                    }
                }
            }
        }
        viewModel.loadQna.observe(this, qnaResult)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}