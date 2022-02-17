package com.example.songil.custom_view.shopping_cart_button

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.ViewShoppingCartButtonBinding
import com.example.songil.page_basket.BasketActivity

class ShoppingCartButton(context: Context, attrs : AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding = ViewShoppingCartButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private val viewModel by lazy { ViewModelProvider(ViewTreeViewModelStoreOwner.get(this)!!)[ShoppingCartButtonViewModel::class.java] }

    init {
        binding.tvShoppingCartCount.text = GlobalApplication.globalSharedPreferences.getInt("count", 0).toString()
        binding.root.setOnClickListener {
            context.startActivity(Intent((context as Activity), BasketActivity::class.java))
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setObserver()
        viewModel.tryGetItemCnt()
    }

    private fun setObserver(){
        val cartItemCntObserver = Observer<Int>{ liveData ->
            binding.tvShoppingCartCount.text = liveData.toString()
        }
        viewModel.cartItemCnt.observe(ViewTreeLifecycleOwner.get(this)!!, cartItemCntObserver)
    }

    fun applyChange(){
        viewModel.tryGetItemCnt()
    }
}