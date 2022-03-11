package com.songil.songil.page_artistmanage.subpage_orderstat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.SimpleBaseActivityBinding
import com.songil.songil.page_shippinginfo.ShippingInfoActivity
import com.songil.songil.recycler.adapter.OrdersArtistPagingAdapter
import com.songil.songil.recycler.rv_interface.RvArtistOrderStatusView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArtistManageOrderStatActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity), RvArtistOrderStatusView {
    private val viewModel : ArtistManageOrderStatViewModel by lazy { ViewModelProvider(this)[ArtistManageOrderStatViewModel::class.java] }
    private var pagingjob : Job?= null

    private val inputShippingInfoResult : ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            (binding.rvContent.adapter as OrdersArtistPagingAdapter).applyInputShippingInfo(parentPosition = targetParentPosition, childPosition = targetChildPosition)
        }
    }

    // RecyclerView 안에 있는 RecyclerView 의 아이템을 수정할 때 해당 아이템에 접근하기 위해 사용할 position 들
    private var targetParentPosition = 0
    private var targetChildPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
        setObserver()
        setRecyclerView()
        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetPageCnt()
        }

        binding.tvTitle.text = getString(R.string.order_status)
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_artist_order_status)

        viewModel.tryGetPageCnt()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = OrdersArtistPagingAdapter(this)
    }

    private fun setObserver(){
        val pageCntObserver = Observer<Int> { resultCode ->
            binding.layoutRefresh.isRefreshing = false
            when (resultCode){
                200 -> {
                    if (viewModel.startPage == 0){
                        binding.viewEmpty.root.visibility = View.VISIBLE
                    } else {
                        binding.viewEmpty.root.visibility = View.GONE
                        restartJob()
                    }
                }
            }
        }
        viewModel.pageCntResult.observe(this, pageCntObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun restartJob(){
        pagingjob?.cancel()
        pagingjob = lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as OrdersArtistPagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    override fun goToInputShippingInfo(parentPosition: Int, childPosition: Int, orderDetailIdx: Int) {
        targetParentPosition = parentPosition
        targetChildPosition = childPosition
        val intent = Intent(this, ShippingInfoActivity::class.java)
        intent.putExtra("ORDER_DETAIL_IDX", orderDetailIdx)
        inputShippingInfoResult.launch(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }
}