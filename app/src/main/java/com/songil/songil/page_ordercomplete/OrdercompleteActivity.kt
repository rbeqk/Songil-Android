package com.songil.songil.page_ordercomplete

import android.content.Intent
import android.os.Bundle
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.OrderActivityCompleteBinding
import com.songil.songil.page_main.MainActivity
import com.songil.songil.utils.setStatusBarBlack

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
            startActivity(intent)
        }

        binding.btnSearchOrderStatus.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra("TARGET_DIRECTION", 1)
            startActivity(intent)
        }
    }
}