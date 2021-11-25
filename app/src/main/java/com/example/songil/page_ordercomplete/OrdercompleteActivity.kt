package com.example.songil.page_ordercomplete

import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.OrderActivityCompleteBinding
import com.example.songil.utils.setStatusBarBlack

class OrdercompleteActivity : BaseActivity<OrderActivityCompleteBinding>(R.layout.order_activity_complete){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarBlack(this, true)
    }
}