package com.example.songil.page_craft

import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.CraftFragmentDetailBinding

class CraftFragmentDetail : BaseFragment<CraftFragmentDetailBinding>(CraftFragmentDetailBinding::bind, R.layout.craft_fragment_detail) {

    var tempFlag = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBuy.setOnClickListener {
            if (tempFlag){
                val anim = TranslateAnimation(0f, 0f, binding.viewCraftAdd.height.toFloat(), 0f)
                anim.duration = 500
                anim.fillAfter = true
                binding.viewCraftAdd.animation = anim
                binding.viewCraftAdd.visibility = View.VISIBLE
                tempFlag = false
            } else {
                val anim = TranslateAnimation(0f, 0f, 0f, binding.viewCraftAdd.height.toFloat())
                anim.duration = 500
                anim.fillAfter = true
                binding.viewCraftAdd.animation = anim
                binding.viewCraftAdd.visibility = View.GONE
                tempFlag = true
            }
        }

    }
}