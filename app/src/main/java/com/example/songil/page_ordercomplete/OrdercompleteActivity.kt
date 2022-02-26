package com.example.songil.page_ordercomplete

import android.content.Intent
import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.OrderActivityCompleteBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.utils.setStatusBarBlack

class OrdercompleteActivity : BaseActivity<OrderActivityCompleteBinding>(R.layout.order_activity_complete){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarBlack(this, true)

        setButton()
    }

    private fun setButton(){
        binding.btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            //onBackPressed()
            startActivity(intent)
        }

        binding.btnSearchOrderStatus.setOnClickListener {

        }
    }
}