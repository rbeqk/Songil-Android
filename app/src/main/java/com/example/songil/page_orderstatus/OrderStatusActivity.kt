package com.example.songil.page_orderstatus

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
import com.example.songil.databinding.SimpleBaseActivityBinding
import com.example.songil.page_cancel.CancelActivity
import com.example.songil.page_commentwrite.CommentWriteActivity
import com.example.songil.page_return.ReturnActivity
import com.example.songil.recycler.adapter.OrdersPagingAdapter
import com.example.songil.recycler.rv_interface.RvUserOrderStatusView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderStatusActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity), RvUserOrderStatusView {

    private val viewModel : OrderStatusViewModel by lazy { ViewModelProvider(this)[OrderStatusViewModel::class.java] }
    private val cancelActivityResult : ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            (binding.rvContent.adapter as OrdersPagingAdapter).updateOrderItemCancelRequest(parentPosition, childPosition)
        }
    }
    private val returnActivityResult : ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            (binding.rvContent.adapter as OrdersPagingAdapter).updateOrderItemReturnRequest(parentPosition, childPosition)
        }
    }
    private val writeCommentActivityResult : ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            (binding.rvContent.adapter as OrdersPagingAdapter).updateOrderItemWriteComment(parentPosition, childPosition)
        }
    }

    private var pagingJob : Job ?= null

    // recyclerView item 업데이트 관련 변수
    // recyclerView 안에 또 다른 recyclerView 가 존재하는데, parentPosition 은 상위 recyclerView 의 position
    // childPosition 은 recyclerView 안의 recyclerView 의 position
    private var parentPosition : Int = 0
    private var childPosition : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.text = getString(R.string.order_status)
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_order_status)

        setRecyclerView()
        setObserver()
        setButton()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryCheckItemEmpty()
        }

        viewModel.tryCheckItemEmpty()
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = OrdersPagingAdapter(this)
    }

    private fun setObserver(){
        viewModel.fetchState.observe(this, baseNetworkErrorObserver)

        val emptyObserver = Observer<Boolean>{ isEmpty ->
            binding.layoutRefresh.isRefreshing = false
            if (isEmpty){
                binding.viewEmpty.root.visibility = View.VISIBLE
                clearJob()
            } else {
                binding.viewEmpty.root.visibility = View.GONE
                restartJob()
            }
        }
        viewModel.itemIsEmpty.observe(this, emptyObserver)
    }

    private fun clearJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as OrdersPagingAdapter).submitData(PagingData.empty())
        }
    }

    private fun restartJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            (binding.rvContent.adapter as OrdersPagingAdapter).submitData(PagingData.empty())
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as OrdersPagingAdapter).submitData(pagingData)
            }
        }
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

    // 각 주문현황에서 주문 취소 요청 버튼을 클릭했을 때 (해당 액티비티 종료시 결과를 받아 아이템 갱신 필요)
    override fun requestCancel(parentPosition: Int, childPosition: Int, orderDetailIdx: Int) {
        this.parentPosition = parentPosition
        this.childPosition = childPosition
        val intent = Intent(this, CancelActivity::class.java)
        intent.putExtra("ORDER_DETAIL_IDX", orderDetailIdx)
        cancelActivityResult.launch(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }

    // 각 주문현황에서 반품 요청 버튼을 클릭했을 때 (해당 액티비티 종료시 결과를 받아 아이템 갱신 필요)
    override fun requestReturn(parentPosition: Int, childPosition: Int, orderDetailIdx: Int) {
        this.parentPosition = parentPosition
        this.childPosition = childPosition
        val intent = Intent(this, ReturnActivity::class.java)
        intent.putExtra("ORDER_DETAIL_IDX", orderDetailIdx)
        returnActivityResult.launch(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }

    // 각 주문현황에서 코멘트 작성 버튼을 클릭했을 때 (해당 액티비티 종료시 결과를 받아 아이템 갱신 필요)
    override fun goToCommentWrite(parentPosition: Int, childPosition: Int, orderDetailIdx: Int) {
        this.parentPosition = parentPosition
        this.childPosition = childPosition
        val intent = Intent(this, CommentWriteActivity::class.java)
        intent.putExtra("ORDER_DETAIL_IDX", orderDetailIdx)
        writeCommentActivityResult.launch(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }
}