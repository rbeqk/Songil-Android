package com.songil.songil.page_basket

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.ShoppingbasketActivityBinding
import com.songil.songil.page_basket.models.AmountAndPosition
import com.songil.songil.page_order.OrderActivity
import com.songil.songil.recycler.adapter.ShoppingCartAdapter

class BasketActivity : BaseActivity<ShoppingbasketActivityBinding>(R.layout.shoppingbasket_activity) {
    private lateinit var viewModel : BasketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BasketViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setRecyclerView()
        setObserver()
        setBtn()

        viewModel.tryGetCartItem()
    }

    private fun setObserver(){
        // when receive cart item list, change view (cart item placed in VM)
        val resultCartItemObserver = Observer<Int>{ liveData ->
            if (liveData == 200){
                (binding.rvShoppingContent.adapter as ShoppingCartAdapter).applyData(viewModel.itemList)
                binding.btnPayment.text = getString(R.string.form_payment, viewModel.getTotalPrice())
                binding.tvSelectAll.text = getString(R.string.select_all_with_count, viewModel.getCheckCount(), viewModel.itemList.size)
            }
        }
        viewModel.itemResultCode.observe(this, resultCartItemObserver)

        // when remove cart item, receive result and if result is successful, change view
        val resultRemoveObserver = Observer<Int> { liveData ->
            if (liveData == 200){
                (binding.rvShoppingContent.adapter as ShoppingCartAdapter).changeData()
                changeActivityViews()
            }
        }
        viewModel.removeItemResult.observe(this, resultRemoveObserver)

        // when toggle top checkbox (can toggle all item)
        val checkAllObserver = Observer<Boolean>{
            changeActivityViews()
        }
        viewModel.checkAll.observe(this, checkAllObserver)

        // when call cart item amount change api, receive result and apply to view
        val amountChangeObserver = Observer<AmountAndPosition> { liveData ->
            (binding.rvShoppingContent.adapter as ShoppingCartAdapter).notifyItemChanged(liveData.position)
            changeActivityViews()
        }
        viewModel.amountChangeResult.observe(this, amountChangeObserver)

        // when cart item cnt is 0, show empty message view
        // otherwise, hide empty message view
        val cartItemCntObserver = Observer<Int> { cnt ->
            when (cnt){
                0 -> {
                    binding.viewEmpty.root.visibility = View.VISIBLE
                    binding.viewEmpty.tvNoProductYet.text = getString(R.string.empty_shopping_basket)
                }
                else -> {
                    binding.viewEmpty.root.visibility = View.GONE
                }
            }
        }
        viewModel.cartItemCnt.observe(this, cartItemCntObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setRecyclerView(){
        binding.rvShoppingContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvShoppingContent.adapter = ShoppingCartAdapter(this, viewModel)
        val animator = binding.rvShoppingContent.itemAnimator
        if (animator is SimpleItemAnimator){
            animator.supportsChangeAnimations = false
        }
    }
    
    private fun setBtn(){
        binding.cbSelectAll.setOnClickListener { // when click top checkbox
            viewModel.changeCbAll() 
            (binding.rvShoppingContent.adapter as ShoppingCartAdapter).changeData()    // send signal to adapter (data select stat is changed)
            changeActivityViews()
        }
        binding.btnPayment.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("ORDER_CRAFTS", viewModel.getOrderCraftForm())
            startActivityHorizontal(intent)
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    // when change data in viewModel, change view to apply VM's data
    private fun changeActivityViews(){
        binding.btnPayment.text = getString(R.string.form_payment, viewModel.getTotalPrice())
        viewModel.paymentBtnActivate.value = (viewModel.getTotalPrice() != 0)
        binding.tvSelectAll.text = getString(R.string.select_all_with_count, viewModel.getCheckCount(), viewModel.itemList.size)
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}