package com.example.songil.page_notice

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.data.Notice
import com.example.songil.databinding.SimpleBaseActivityBinding
import com.example.songil.recycler.adapter.NoticeAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NoticeActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity) {

    private val viewModel : NoticeViewModel by lazy { ViewModelProvider(this)[NoticeViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.text = getString(R.string.notice)

        setButton()
        setRecyclerView()

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as NoticeAdapter).submitData(pagingData)
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = NoticeAdapter(NoticeComparator)
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

    object NoticeComparator : DiffUtil.ItemCallback<Notice>(){
        override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return (oldItem.title == newItem.title)
        }

    }
}