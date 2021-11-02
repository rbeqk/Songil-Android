package com.example.songil.page_craft

import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.CraftActivityBinding

class CraftActivity : BaseActivity<CraftActivityBinding>(R.layout.craft_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, CraftFragmentDetail()).commit()
    }
}