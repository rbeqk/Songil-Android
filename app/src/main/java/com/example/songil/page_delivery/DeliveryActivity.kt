package com.example.songil.page_delivery

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.DeliveryActivityBinding
import com.example.songil.recycler.adapter.DeliveryAdapter

class DeliveryActivity : BaseActivity<DeliveryActivityBinding>(R.layout.delivery_activity) {

    private val viewModel : DeliveryViewModel by lazy { ViewModelProvider(this)[DeliveryViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        setRecyclerView()
        setButton()
        setObserver()

        viewModel.tempGetDelivery()
    }

    private fun setObserver(){
        val tempResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvContent.adapter as DeliveryAdapter).applyData(viewModel.dataList)
                }
            }
        }
        viewModel.deliveryResult.observe(this, tempResult)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = DeliveryAdapter()
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}