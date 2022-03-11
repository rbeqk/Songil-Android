package com.songil.songil.custom_view.shopping_cart_button

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.databinding.ViewShoppingCartButtonBinding
import com.songil.songil.page_basket.BasketActivity
import com.songil.songil.popup_warning.NeedLoginDialog
import java.lang.Exception


class ShoppingCartButton(context: Context, attrs : AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding = ViewShoppingCartButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private val viewModel by lazy { ViewModelProvider(ViewTreeViewModelStoreOwner.get(this)!!)[ShoppingCartButtonViewModel::class.java] }

    init {
        binding.tvShoppingCartCount.text = GlobalApplication.globalSharedPreferences.getInt("count", 0).toString()
        binding.root.setOnClickListener {
            if (GlobalApplication.checkIsLogin()){
                (context as BaseActivity<*>).startActivityHorizontal(Intent(context, BasketActivity::class.java))
            } else {
                try {
                    val dialog = NeedLoginDialog()
                    dialog.show((context as BaseActivity<*>).supportFragmentManager, dialog.tag)
                } catch (e : Exception) {
                    // context 가 BaseActivity 로 casting 이 안될 때
                }
            }
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