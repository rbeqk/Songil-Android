package com.example.songil.page_story.subpage_story_chat

import android.os.Bundle
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

class StoryChatActivity : BaseActivity<ChatActivityBinding>(R.layout.chat_activity) {

    private val viewModel : StoryChatViewModel by lazy { ViewModelProvider(this)[StoryChatViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.layoutRefresh.setOnRefreshListener {
            (binding.rvComment.adapter as PostAndChatAdapter).refresh()
            binding.layoutRefresh.isRefreshing = false
        }

        viewModel.storyIdx = intent.getIntExtra(GlobalApplication.STORY_IDX, 0)

        setRecyclerView()
        setButton()

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvComment.adapter as PostAndChatAdapter).submitData(pagingData)
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvComment.adapter = PostAndChatAdapter()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}