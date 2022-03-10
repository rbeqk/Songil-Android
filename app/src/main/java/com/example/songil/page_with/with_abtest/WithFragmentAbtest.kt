package com.example.songil.page_with.with_abtest

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
import com.example.songil.page_abtest.AbtestActivity
import com.example.songil.page_with.WithSubFragmentInterface
import com.example.songil.recycler.adapter.WithABTestPagingAdapter
import com.example.songil.recycler.rv_interface.RvAbTestView
import com.example.songil.recycler.rv_interface.RvSingleUpdateView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WithFragmentAbtest : BaseFragment<SimpleRecyclerviewFragmentSwipeBinding>(SimpleRecyclerviewFragmentSwipeBinding::bind, R.layout.simple_recyclerview_fragment_swipe), WithSubFragmentInterface, RvAbTestView, RvSingleUpdateView {

    private val viewModel : WithAbtestViewModel by lazy { ViewModelProvider(this)[WithAbtestViewModel::class.java] }
    private var pagingJob : Job? = null
    private var isFirst = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setRecyclerView()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetPageCnt()
        }

    }

    private fun setObserver(){
        val pageCntResult = Observer<Int>{ _ ->
            binding.layoutRefresh.isRefreshing = false
            viewModel.isRefresh = true
            initAndLoad()
            (binding.rvContent.adapter as WithABTestPagingAdapter).refresh()
        }
        viewModel.startIdx.observe(viewLifecycleOwner, pageCntResult)

        val voteResult = Observer<Boolean>{ liveData ->
            if (liveData){
                (binding.rvContent.adapter as WithABTestPagingAdapter).refresh()
            }
        }
        viewModel.voteResult.observe(viewLifecycleOwner, voteResult)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = WithABTestPagingAdapter(this)
        (binding.rvContent.adapter as WithABTestPagingAdapter).attachSingleUpdateView(this)
    }

    private fun initAndLoad(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as WithABTestPagingAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as WithABTestPagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun onShow() {
        if (!isFirst){
            binding.rvContent.scrollToPosition(0)
        } else {
            viewModel.tryGetPageCnt()
            isFirst = false
        }
    }

    override fun sort(sort: String) {
        viewModel.sort = sort
        viewModel.tryGetPageCnt()
    }

    override fun getSort(): String = viewModel.sort

    override fun reload() {
        viewModel.pointer?.invalidate()  //(binding.rvContent.adapter as WithABTestPagingAdapter).refresh()
    }

    override fun vote(abTestIdx: Int, vote: String) {
        if (GlobalApplication.checkIsLogin()){
            viewModel.tryVote(abTestIdx, vote)
        } else {
            (activity as BaseActivity<*>).callNeedLoginDialog()
        }
    }

    override fun cancelVote(abTestIdx: Int) {
        if (GlobalApplication.checkIsLogin()){
            viewModel.tryCancelVote(abTestIdx)
        } else {
            (activity as BaseActivity<*>).callNeedLoginDialog()
        }
    }

    override var recentTargetPosition: Int = 0

    override val singleItemUpdateCallback: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            result.data?.let {
                val item = it.getSerializableExtra("ABTEST")
                if (item == null){
                    (binding.rvContent.adapter as WithABTestPagingAdapter).refresh()
                } else {
                    (binding.rvContent.adapter as WithABTestPagingAdapter).applySingleItemChange(recentTargetPosition, item)
                }
            }
        }
    }

    override fun targetItemClick(position: Int, targetIdx: Int) {
        recentTargetPosition = position
        val intent = Intent(context, AbtestActivity::class.java)
        intent.putExtra(GlobalApplication.ABTEST_IDX, targetIdx)
        singleItemUpdateCallback.launch(intent)
        (activity as BaseActivity<*>).overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }

}