package com.songil.songil.page_artistmanage.subpage_asklist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.BaseViewModel
import com.songil.songil.config.GlobalApplication
import com.songil.songil.databinding.SimpleBaseActivityBinding
import com.songil.songil.page_artistmanage.subpage_answer.ArtistManageAnswerActivity
import com.songil.songil.recycler.adapter.Craft3ArtistAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArtistManageAskListActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity) {

    private val viewModel : ArtistManageAskListViewModel by lazy { ViewModelProvider(this)[ArtistManageAskListViewModel::class.java] }
    private var selectPosition = 0
    private val getWriteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){ (binding.rvContent.adapter as Craft3ArtistAdapter).applyChange(selectPosition) }
    }
    private var pagingJob : Job ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.text = getString(R.string.one_by_one_ask_list)
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_inquiry_list_artist)

        setRecyclerView()
        setObserver()
        setButton()

        viewModel.tryGetPageCnt()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetPageCnt()
        }
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setObserver() {
        val startCntObserver = Observer<Int>{ totalPage ->
            binding.layoutRefresh.isRefreshing = false
            if (totalPage > 0){
                binding.viewEmpty.root.visibility = View.GONE
                restartJob()
            } else {
                binding.viewEmpty.root.visibility = View.VISIBLE
            }
        }
        viewModel.pageCntResult.observe(this, startCntObserver)

        val errorObserver = Observer<BaseViewModel.FetchState>{ fetchState ->
            binding.layoutRefresh.isRefreshing = false
            when (fetchState){
                BaseViewModel.FetchState.BAD_INTERNET -> {
                    showSimpleToastMessage(getString(R.string.bad_internet))
                }
                BaseViewModel.FetchState.FAIL -> {
                    showSimpleToastMessage(getString(R.string.bad_internet))
                }
                BaseViewModel.FetchState.WRONG_CONNECTION -> {
                    showSimpleToastMessage(getString(R.string.bad_internet))
                }
                BaseViewModel.FetchState.PARSE_ERROR -> {}
                null -> {}
            }
        }
        viewModel.fetchState.observe(this, errorObserver)
    }

    private fun restartJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as Craft3ArtistAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as Craft3ArtistAdapter).submitData(pagingData)
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = Craft3ArtistAdapter(::enterWritePage)
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    private fun enterWritePage(position : Int, askIdx : Int, status : Int){
        selectPosition = position
        val intent = Intent(this, ArtistManageAnswerActivity::class.java)
        intent.putExtra(GlobalApplication.TARGET_IDX, askIdx)
        intent.putExtra("ANSWER", status)
        getWriteResult.launch(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }
}