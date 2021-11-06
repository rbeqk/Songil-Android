package com.example.songil.page_craft.subpage_detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.CraftFragmentDetailBinding
import com.example.songil.page_craft.CraftActivity
import com.example.songil.page_craft.models.CraftDetailInfo
import com.example.songil.recycler.adapter.CraftImageAdapter
import com.example.songil.recycler.decoration.CraftImageDecoration

class CraftFragmentDetail(private var isLike : Int, private val detailInfo: CraftDetailInfo) : BaseFragment<CraftFragmentDetailBinding>(CraftFragmentDetailBinding::bind, R.layout.craft_fragment_detail) {

    //var tempFlag = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.btnBuy.setOnClickListener {
            if (tempFlag){
                val anim = TranslateAnimation(0f, 0f, binding.viewCraftAdd.height.toFloat(), 0f)
                anim.duration = 500
                anim.fillAfter = false
                binding.viewCraftAdd.animation = anim
                binding.viewCraftAdd.visibility = View.VISIBLE
                tempFlag = false
            } else {
                val anim = TranslateAnimation(0f, 0f, 0f, binding.viewCraftAdd.height.toFloat())
                anim.duration = 500
                anim.fillAfter = false
                binding.viewCraftAdd.animation = anim
                binding.viewCraftAdd.visibility = View.GONE
                tempFlag = true
            }
        }*/

        applyView()
    }

    private fun applyView(){
        binding.tvCraftDescriptionContent.text = detailInfo.productIntro
        binding.tvSizeValue.text = getString(R.string.form_craft_size, detailInfo.width, detailInfo.depth, detailInfo.height)
        binding.tvWarningNoticeContent.text = detailInfo.caution
        binding.rvDetailImage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvDetailImage.adapter = CraftImageAdapter(activity as CraftActivity, detailInfo.imgs)
        binding.rvDetailImage.addItemDecoration(CraftImageDecoration(activity as CraftActivity))
    }
}