package com.example.songil.page_order

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.OrderActivityBinding
import com.example.songil.recycler.adapter.Craft4Adapter
import com.example.songil.webview_address.WebAddressActivity

class OrderActivity : BaseActivity<OrderActivityBinding>(R.layout.order_activity){

    private val orderViewModel : OrderViewModel by lazy { ViewModelProvider(this)[OrderViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = orderViewModel
        binding.lifecycleOwner = this

        if (!GlobalApplication.globalSharedPreferences.contains(GlobalApplication.X_ACCESS_TOKEN)){
            binding.layoutBenefit.visibility =  View.GONE
            binding.spaceCoupon.visibility = View.GONE
        }

        setRecyclerView()
        setObserver()
        setButton()

        orderViewModel.tryGetOrderForm()
    }

    private fun setButton(){
        val addressResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                val address = result.data?.getStringExtra("address")?.split(",") ?: arrayListOf()
                if (address.size == 2) {
                    binding.etZipCode.setText(address[0])
                    binding.etAddress.setText(address[1])
                }
            }
        }

        binding.btnSearchAddress.setOnClickListener {
            addressResult.launch(Intent(this, WebAddressActivity::class.java))
        }
    }

    private fun setRecyclerView(){
        binding.rvCraft.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvCraft.adapter = Craft4Adapter()
    }

    private fun setObserver(){
        val orderFormObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvCraft.adapter as Craft4Adapter).applyData(orderViewModel.craftList)
                }
            }
        }
        orderViewModel.orderFormResult.observe(this, orderFormObserver)
    }
}