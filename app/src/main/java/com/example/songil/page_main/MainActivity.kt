package com.example.songil.page_main

import android.content.Intent
import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.MainActivityBinding
import com.example.songil.page_article.ArticleFragmentMain
import com.example.songil.page_home.HomeFragment
import com.example.songil.page_mypage.MypaegFragment
import com.example.songil.page_needlogin.NeedLoginActivity
import com.example.songil.page_shop.ShopFragmentMain
import com.example.songil.utils.checkUserIdx
//import com.example.songil.utils.setStatusBar

class MainActivity : BaseActivity<MainActivityBinding>(R.layout.main_activity){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottom.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_home -> {
                    supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, HomeFragment()).commit()
                }
                R.id.bottom_shop -> {
                    //startActivity(Intent(this, CraftActivity::class.java))
                    supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, ShopFragmentMain()).commit()
                }
                R.id.bottom_with -> {

                }
                R.id.bottom_article -> {
                    //setStatusBar(this, true) // 개선 필요
                    supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, ArticleFragmentMain()).commit()
                }
                else -> {
                    if (checkUserIdx()){
                        supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, MypaegFragment()).commit()
                    } else {
                        startActivity(Intent(this, NeedLoginActivity::class.java))
                    }
                    //supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, MypaegFragment()).commit()
                }
            }
            true
        }
        binding.bottom.selectedItemId = R.id.bottom_home
    }

    fun moveToHome(){
        binding.bottom.selectedItemId = R.id.bottom_shop
    }
}