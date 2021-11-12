package com.example.songil.custom_view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.ViewShoppingCartButtonBinding
import com.example.songil.page_basket.BasketActivity

class ShoppingCartButton(context: Context, attrs : AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding = ViewShoppingCartButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.tvShoppingCartCount.text = GlobalApplication.globalSharedPreferences.getInt("count", 0).toString()
        binding.root.setOnClickListener {
            context.startActivity(Intent((context as Activity), BasketActivity::class.java))
        }
    }

    override fun invalidate() {
        super.invalidate()
        binding.tvShoppingCartCount.text = GlobalApplication.globalSharedPreferences.getInt("count", 1).toString()
    }
}