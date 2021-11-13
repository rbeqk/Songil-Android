package com.example.songil.page_orderstatus

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.data.Orders
import com.example.songil.databinding.OrderstatusActivityBinding
import com.example.songil.recycler.adapter.RvOrdersAdapter

class OrderstatusActivity : BaseActivity<OrderstatusActivityBinding>(R.layout.orderstatus_activity) {

    private lateinit var viewModel : OrderstatusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[OrderstatusViewModel::class.java]
        setRecyclerView()
        setObserver()
        viewModel.getData()
    }

    private fun setRecyclerView(){
        binding.rvOrders.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvOrders.adapter = RvOrdersAdapter(this)
    }

    private fun setObserver(){
        val orderStatusObserver = Observer<ArrayList<Orders>>{ liveData ->
            (binding.rvOrders.adapter as RvOrdersAdapter).applyData(liveData)
        }
        viewModel.ordersData.observe(this, orderStatusObserver)
    }
}