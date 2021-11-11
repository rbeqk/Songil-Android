package com.example.songil.page_basket

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.ShoppingbasketActivityBinding
import com.example.songil.recycler.adapter.BasketRvCraftAdapter
import com.example.songil.recycler.rv_interface.RvTriggerView

class BasketActivity : BaseActivity<ShoppingbasketActivityBinding>(R.layout.shoppingbasket_activity), RvTriggerView {
    private lateinit var viewModel : BasketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BasketViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setRecyclerView()
        setObserver()
        setBtn()

        viewModel.tryGetCart()
    }

    private fun setObserver(){
        val resultCodeObserver = Observer<Int>{ liveData ->
            if (liveData == 1000){
                (binding.rvShoppingContent.adapter as BasketRvCraftAdapter).applyData(viewModel.itemList)
                binding.btnPayment.text = getString(R.string.form_price_won, viewModel.getTotalPrice())
                //viewModel.checkAll.value = viewModel.checkAllCbSelected()
                binding.tvSelectAll.text = getString(R.string.select_all_with_count, viewModel.getCheckCount(), viewModel.itemList.size)
            }
        }
        viewModel.itemResultCode.observe(this, resultCodeObserver)
    }

    private fun setRecyclerView(){
        binding.rvShoppingContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvShoppingContent.adapter = BasketRvCraftAdapter(this, this)
        val animator = binding.rvShoppingContent.itemAnimator
        if (animator is SimpleItemAnimator){
            animator.supportsChangeAnimations = false
        }
    }
    
    private fun setBtn(){
        binding.cbSelectAll.setOnClickListener { // 전체 선택 버튼 클릭 이벤트 
            viewModel.changeCbAll() 
            (binding.rvShoppingContent.adapter as BasketRvCraftAdapter).changeData()    // adapter 에 데이터 변화 전달
            changeActivityViews()
        }
        binding.btnPayment.setOnClickListener {
            Log.d("test", "payment ${viewModel.itemList}")
        }
        binding.btnBack.setOnClickListener {
            Log.d("test", "${viewModel.itemList}")
        }
    }

    private fun changeActivityViews(){
        binding.btnPayment.text = getString(R.string.form_price_won, viewModel.getTotalPrice())
        viewModel.paymentBtnActivate.value = (viewModel.getTotalPrice() != 0)

        binding.tvSelectAll.text = getString(R.string.select_all_with_count, viewModel.getCheckCount(), viewModel.itemList.size)
    }

    // call in adapter
    override fun notifyDataChange(type : Int, position : Int?) {
        when (type){
            0 -> { // check 만 변경, 서버 호출 불필요
                viewModel.checkAll.value = viewModel.checkAllCbSelected()
            }
            1 -> { // 개수 조정
                viewModel.tryChangeItemCount(position!!)
            }
            else -> {   // 삭제
                viewModel.tryDeleteItem(position!!)
            }
        }
        changeActivityViews()
    }

    // Activity 의 notifyDataChange -> Adapter 에서 Activity 로 데이터 변화 알림
    // Adapter 의 changeData -> Activity 에서 Adapter 로 데이터 변화 알림
}