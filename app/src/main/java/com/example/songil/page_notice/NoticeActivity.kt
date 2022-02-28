package com.example.songil.page_notice

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseViewModel
import com.example.songil.databinding.SimpleBaseActivityBinding
import com.example.songil.popup_warning.SocketTimeoutDialog
import com.example.songil.recycler.adapter.NoticeAdapter

class NoticeActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity) {

    private val viewModel : NoticeViewModel by lazy { ViewModelProvider(this)[NoticeViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.text = getString(R.string.notice)
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_notice)

        setButton()
        setRecyclerView()
        setObserver()

        viewModel.tryGetNotice()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetNotice()
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = NoticeAdapter()
    }

    private fun setObserver(){
        val getNoticeObserver = Observer<Int> { resultCode ->
            binding.layoutRefresh.isRefreshing = false
            when (resultCode){
                200 -> {
                    if (viewModel.noticeList.size == 0){
                        binding.viewEmpty.root.visibility = View.VISIBLE
                    } else {
                        (binding.rvContent.adapter as NoticeAdapter).applyData(viewModel.noticeList)
                        binding.viewEmpty.root.visibility = View.GONE
                    }
                }
            }
        }
        viewModel.getNoticeResult.observe(this, getNoticeObserver)

        val errorObserver = Observer<BaseViewModel.FetchState>{ _ ->
            val socketTimeoutDialog = SocketTimeoutDialog()
            socketTimeoutDialog.show(supportFragmentManager, socketTimeoutDialog.tag)
        }
        viewModel.fetchState.observe(this, errorObserver)
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