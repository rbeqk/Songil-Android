package com.example.songil.page_main

import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.MainActivityBinding
import com.example.songil.page_article.ArticleFragmentMain
import com.example.songil.page_artistmanage.ArtistManageFragment
import com.example.songil.page_home.HomeFragment
import com.example.songil.page_mypage.MypaegFragment
import com.example.songil.page_shop.ShopFragmentMain
import com.example.songil.page_with.WithFragment
import com.example.songil.utils.setStatusBarBlack

class MainActivity : BaseActivity<MainActivityBinding>(R.layout.main_activity){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottom.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_home -> {
                    setStatusBarBlack(this, false)
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(binding.layoutFragment.id, HomeFragment()).commit()
                }
                R.id.bottom_shop -> {
                    setStatusBarBlack(this, false)
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(binding.layoutFragment.id, ShopFragmentMain()).commit()
                }
                R.id.bottom_with -> {
                    setStatusBarBlack(this, true)
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(binding.layoutFragment.id, WithFragment()).commit()
                }
                R.id.bottom_article -> {
                    setStatusBarBlack(this, true)
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(binding.layoutFragment.id, ArticleFragmentMain()).commit()
                }
                else -> {
                    setStatusBarBlack(this, false)
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(binding.layoutFragment.id, MypaegFragment()).commit()
                }
            }
            true
        }
        binding.bottom.selectedItemId = R.id.bottom_home
    }

    fun toggleMyPage(toArtist : Boolean){
        if (toArtist) { supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, ArtistManageFragment()).commit() }
        else { supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, MypaegFragment()).commit() }
    }
}