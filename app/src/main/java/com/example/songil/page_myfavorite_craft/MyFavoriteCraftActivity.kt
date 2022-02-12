package com.example.songil.page_myfavorite_craft

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.MydetailActivityBinding
import com.example.songil.recycler.adapter.Craft1PagingAdapter
import com.example.songil.recycler.decoration.Craft1Decoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyFavoriteCraftActivity : BaseActivity<MydetailActivityBinding>(R.layout.mydetail_activity) {

    private val viewModel : MyFavoriteCraftViewModel by lazy { ViewModelProvider(this)[MyFavoriteCraftViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.text = "찜한 상품"
        setRecyclerView()
        setObserver()
        setButton()

        viewModel.tryGetFavoriteCraftCnt()

        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_my_favorite)

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetFavoriteCraftCnt()
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = GridLayoutManager(this, 2)
        binding.rvContent.adapter = Craft1PagingAdapter()
        binding.rvContent.addItemDecoration(Craft1Decoration(this, true))
    }

    private fun setObserver(){
        val cntObserver = Observer<Int>{ liveData ->
            binding.layoutRefresh.isRefreshing = false
            if (liveData == 0) {
                binding.viewEmpty.root.visibility = View.VISIBLE
            } else {
                binding.viewEmpty.root.visibility = View.GONE
                lifecycleScope.launch {
                    viewModel.flow.collectLatest { pagingData ->
                        (binding.rvContent.adapter as Craft1PagingAdapter).submitData(pagingData)
                    }
                }
            }
        }
        viewModel.totalCnt.observe(this, cntObserver)
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