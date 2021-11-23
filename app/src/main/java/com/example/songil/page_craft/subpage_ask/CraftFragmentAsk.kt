package com.example.songil.page_craft.subpage_ask

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.CraftFragmentAskBinding
import com.example.songil.page_craft.CraftActivity

class CraftFragmentAsk(private val profileImg : String?, private val artistName : String) : BaseFragment<CraftFragmentAskBinding>(CraftFragmentAskBinding::bind, R.layout.craft_fragment_ask) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyView()
    }

    private fun applyView(){
        Glide.with(activity as CraftActivity).load(profileImg).into(binding.ivProfile)
        binding.tvArtist.text = artistName
        binding.tvQuestAboutValue.text = "작품"
        binding.tvQuestAbout2Value.text = "주문 및 포장"
    }
}