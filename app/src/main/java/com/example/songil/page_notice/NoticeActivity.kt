package com.example.songil.page_notice

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.data.WithNotice
import com.example.songil.databinding.NoticeActivityBinding
import com.example.songil.recycler.adapter.NoticeAdapter

class NoticeActivity : BaseActivity<NoticeActivityBinding>(R.layout.notice_activity){

    private val viewModel : NoticeViewModel by lazy { ViewModelProvider(this)[NoticeViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
        setRecyclerView()
        setObserver()

        viewModel.tryGetNotice()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView(){
        binding.rvNotice.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvNotice.adapter = NoticeAdapter()
    }

    private fun setObserver(){
        val noticeObserver = Observer<ArrayList<WithNotice>>{ liveData ->
            (binding.rvNotice.adapter as NoticeAdapter).applyData(liveData)
        }
        viewModel.noticeList.observe(this, noticeObserver)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.from_left_30, R.anim.to_right)
    }
}