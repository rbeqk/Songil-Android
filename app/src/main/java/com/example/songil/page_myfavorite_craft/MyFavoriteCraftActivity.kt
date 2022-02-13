package com.example.songil.page_myfavorite_craft

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseViewModel
import com.example.songil.databinding.MydetailActivityBinding
import com.example.songil.popup_warning.SocketTimeoutDialog
import com.example.songil.recycler.adapter.Craft1PagingAdapter
import com.example.songil.recycler.decoration.Craft1Decoration
import com.example.songil.utils.dpToPx
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyFavoriteCraftActivity : BaseActivity<MydetailActivityBinding>(R.layout.mydetail_activity) {

    private val viewModel : MyFavoriteCraftViewModel by lazy { ViewModelProvider(this)[MyFavoriteCraftViewModel::class.java] }
    private var pagingJob : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.text = getString(R.string.favorite_product)
        setRecyclerView()
        setObserver()
        setButton()

        viewModel.tryGetFavoriteCraftCnt()

        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_my_favorite)

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetFavoriteCraftCnt()
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.tryGetFavoriteCraftCnt()
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = GridLayoutManager(this, 2)
        binding.rvContent.adapter = Craft1PagingAdapter()
        binding.rvContent.addItemDecoration(Craft1Decoration(this, true))
        binding.rvContent.setPadding(dpToPx(this, 12), 0, dpToPx(this, 12), 0)
    }

    private fun setObserver(){
        val cntObserver = Observer<Int>{ liveData ->
            binding.layoutRefresh.isRefreshing = false
            if (liveData == 0) {
                binding.viewEmpty.root.visibility = View.VISIBLE
                clearJob()
            } else {
                binding.viewEmpty.root.visibility = View.GONE
                if ((binding.rvContent.adapter as Craft1PagingAdapter).itemCount > 0){
                    (binding.rvContent.adapter as Craft1PagingAdapter).refresh()
                } else {
                    restartJob()
                }
            }
        }
        viewModel.totalCnt.observe(this, cntObserver)

        val errorObserver = Observer<BaseViewModel.FetchState>{ liveData ->
            when(liveData){
                BaseViewModel.FetchState.BAD_INTERNET -> {
                    val timeOutDialog = SocketTimeoutDialog()
                    timeOutDialog.show(supportFragmentManager, timeOutDialog.tag)
                }
                else -> {

                }
            }
        }
        viewModel.fetchState.observe(this, errorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun clearJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as Craft1PagingAdapter).submitData(PagingData.empty())
        }
    }

    private fun restartJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as Craft1PagingAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as Craft1PagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}