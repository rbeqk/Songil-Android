package com.example.songil.page_with.with_qna

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseFragment
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.SimpleRecyclerviewFragmentSwipeBinding
import com.example.songil.page_qna.QnaActivity
import com.example.songil.page_with.WithSubFragmentInterface
import com.example.songil.recycler.adapter.WithQnaPagingAdapter
import com.example.songil.recycler.rv_interface.RvSingleUpdateView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WithFragmentQna : BaseFragment<SimpleRecyclerviewFragmentSwipeBinding>(SimpleRecyclerviewFragmentSwipeBinding::bind, R.layout.simple_recyclerview_fragment_swipe), WithSubFragmentInterface,
    RvSingleUpdateView {

    private val viewModel: WithQnaViewModel by lazy { ViewModelProvider(this)[WithQnaViewModel::class.java] }
    private var pagingJob : Job ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()
        setRecyclerView()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetPageCnt()
        }

        viewModel.tryGetPageCnt()
    }

    private fun setObserver(){
        val pageCntResult = Observer<Int> { _ ->
            binding.layoutRefresh.isRefreshing = false
            viewModel.isRefresh = true
            initAndLoad()
            (binding.rvContent.adapter as WithQnaPagingAdapter).refresh()
        }
        viewModel.startIdx.observe(viewLifecycleOwner, pageCntResult)
    }

    private fun initAndLoad(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as WithQnaPagingAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as WithQnaPagingAdapter).submitData(pagingData)
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = WithQnaPagingAdapter()
        (binding.rvContent.adapter as WithQnaPagingAdapter).attachSingleUpdateView(this)
    }

    override fun onShow() {
        binding.rvContent.scrollToPosition(0)
    }

    override fun sort(sort: String) {
        viewModel.sort = sort
        viewModel.tryGetPageCnt()
    }

    override fun getSort(): String = viewModel.sort

    override fun reload() {
        //(binding.rvContent.adapter as WithQnaPagingAdapter).refresh()
    }

    override var recentTargetPosition: Int = 0

    override val singleItemUpdateCallback: ActivityResultLauncher<Intent> =  registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            result.data?.let {
                val item = it.getSerializableExtra("QNA")
                if (item == null){
                    (binding.rvContent.adapter as WithQnaPagingAdapter).refresh()
                } else {
                    (binding.rvContent.adapter as WithQnaPagingAdapter).applySingleItemChange(recentTargetPosition, item)
                }
            }
        }
    }

    override fun targetItemClick(position: Int, targetIdx: Int) {
        recentTargetPosition = position
        val intent = Intent(context, QnaActivity::class.java)
        intent.putExtra(GlobalApplication.QNA_IDX, targetIdx)
        singleItemUpdateCallback.launch(intent)
        (activity as BaseActivity<*>).overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }
}