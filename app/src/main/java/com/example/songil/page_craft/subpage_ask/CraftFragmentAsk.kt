package com.example.songil.page_craft.subpage_ask

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.CraftFragmentAskBinding
import com.example.songil.page_craft.CraftActivity
import com.example.songil.utils.dpToPx

class CraftFragmentAsk : BaseFragment<CraftFragmentAskBinding>(CraftFragmentAskBinding::bind, R.layout.craft_fragment_ask) {
    private var profileImg : String? = null
    private var artistName : String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutParams = binding.layoutMain.layoutParams
        layoutParams.height = getWindowSize()[1] - (activity as CraftActivity).getToolbarHeight() - getStatusBarHeight() - dpToPx(requireContext(), 56)
        binding.layoutMain.layoutParams = layoutParams
    }

    private fun applyView(){
        Glide.with(activity as CraftActivity).load(profileImg).into(binding.ivProfile)
        binding.tvArtist.text = artistName
        binding.tvQuestAboutValue.text = "작품"
        binding.tvQuestAbout2Value.text = "주문 및 포장"
    }

    fun applyData(inputProfileImg : String?, inputArtistName : String){
        profileImg = inputProfileImg
        artistName = inputArtistName
        applyView()
    }
}