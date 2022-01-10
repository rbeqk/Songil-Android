package com.example.songil.page_order

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser

// 여기 부트페이 테스트 api key 있다. commit 금지
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

        BootpayAnalytics.init(this, "61c91047e38c30001ed2cfd6")

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

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnPayment.setOnClickListener {
            goBootPayRequest()
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

    private fun goBootPayRequest() {
        val bootUser = BootUser().setPhone("010-7748-8084")
        val bootExtra = BootExtra().setQuotas(intArrayOf(0, 2, 3))
        Bootpay.init(this).setApplicationId("61c91047e38c30001ed2cfd6").setContext(this)
            .setBootUser(bootUser).setBootExtra(bootExtra).setUX(UX.PG_DIALOG).setPG(PG.KCP).setMethod(Method.CARD)
            .setName("테스트 상품명").setOrderId("1234").setPrice(1000).onDone { message ->
                Log.d("done", message)
            }
            .onReady { message ->
                Log.d("ready", message)
            }
            .onCancel { message ->
                Log.d("cancel", message)
            }
            .onError{ message ->
                Log.d("error", message)
            }
            .onClose { _ ->
                Log.d("close", "close")
            }
            .request()
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}