package com.example.songil.page_mypage_about_post

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseViewModel
import com.example.songil.config.MyPageActivityType
import com.example.songil.databinding.MydetailActivityBinding
import com.example.songil.popup_warning.SocketTimeoutDialog
import com.example.songil.recycler.adapter.PostPagingAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// 댓글단 글, 내가 쓴 글, 좋아요한 게시글 조회 페이지
class MyPagePostActivity : BaseActivity<MydetailActivityBinding>(R.layout.mydetail_activity) {

    private val viewModel : MyPagePostViewModel by lazy { ViewModelProvider(this)[MyPagePostViewModel::class.java] }
    private var pagingJob : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getSerializableExtra("MyPageActivityType") as MyPageActivityType
        setTitleAndEmptyText(type)
        viewModel.setInit(type)

        setRecyclerView()
        setButton()
        setObserver()


        viewModel.tryGetCnt()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetCnt()
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.tryGetCnt()
    }

    private fun setTitleAndEmptyText(type: MyPageActivityType){
        when(type){
            MyPageActivityType.COMMENT_POST -> {
                binding.tvTitle.text = getString(R.string.comment_post)
                binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_my_comment_post)
            }
            MyPageActivityType.MY_POST -> {
                binding.tvTitle.text =  getString(R.string.my_write)
                binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_my_wrote_post)
            }
            MyPageActivityType.FAVORITE_POST -> {
                binding.tvTitle.text = getString(R.string.like_post)
                binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_my_favorite_post)
            }
        }


    }

    private fun setObserver(){
        val cntObserver = Observer<Int>{ liveData ->
            binding.layoutRefresh.isRefreshing = false
            if (liveData == 0) {
                binding.viewEmpty.root.visibility = View.VISIBLE
                clearJob()
            } else {
                binding.viewEmpty.root.visibility = View.GONE
                if ((binding.rvContent.adapter as PostPagingAdapter).itemCount > 0){
                    (binding.rvContent.adapter as PostPagingAdapter).refresh()
                } else {
                    restartJob()
                }
            }
        }
        viewModel.totalCnt.observe(this, cntObserver)

        val errorObserver = Observer<BaseViewModel.FetchState> { _ ->
            val dialog = SocketTimeoutDialog()
            dialog.show(supportFragmentManager, dialog.tag)
        }
        viewModel.fetchState.observe(this, errorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = PostPagingAdapter()
    }

    private fun clearJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as PostPagingAdapter).submitData(PagingData.empty())
        }
    }

    private fun restartJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as PostPagingAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as PostPagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}