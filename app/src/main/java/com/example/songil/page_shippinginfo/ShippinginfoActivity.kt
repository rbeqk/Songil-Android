package com.example.songil.page_shippinginfo

import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.ShippinginfoActivityInputBinding

class ShippinginfoActivity : BaseActivity<ShippinginfoActivityInputBinding>(R.layout.shippinginfo_activity_input) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}