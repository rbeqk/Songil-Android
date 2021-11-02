package com.example.songil.page_main

import android.content.Intent
import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.MainActivityBinding
import com.example.songil.page_article.ArticleFragmentMain
import com.example.songil.page_craft.CraftActivity
import com.example.songil.utils.setStatusBar

class MainActivity : BaseActivity<MainActivityBinding>(R.layout.main_activity){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottom.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_home -> {

                }
                R.id.bottom_shop -> {
                    startActivity(Intent(this, CraftActivity::class.java))
                }
                R.id.bottom_with -> {

                }
                R.id.bottom_article -> {
                    //setStatusBar(this, true) // 개선 필요
                    supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, ArticleFragmentMain()).commit()
                }
                else -> {

                }
            }
            true
        }
    }
}