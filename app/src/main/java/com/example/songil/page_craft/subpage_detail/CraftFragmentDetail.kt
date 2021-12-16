package com.example.songil.page_craft.subpage_detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.data.ProductDetailInfo
import com.example.songil.databinding.CraftFragmentDetailBinding
import com.example.songil.page_craft.CraftActivity
import com.example.songil.recycler.adapter.SimpleImageAdapter
import com.example.songil.recycler.decoration.SimpleImageDecoration

class CraftFragmentDetail(private val detailInfo: ProductDetailInfo) : BaseFragment<CraftFragmentDetailBinding>(CraftFragmentDetailBinding::bind, R.layout.craft_fragment_detail) {

    //var tempFlag = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyView()
    }

    private fun applyView(){
        binding.tvCraftDescriptionContent.text = detailInfo.content
        binding.tvSizeValue.text = detailInfo.size
        binding.tvWarningNoticeContent.text = setCautionString()
        binding.rvDetailImage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvDetailImage.adapter = SimpleImageAdapter(activity as CraftActivity, detailInfo.detailImageUrls)
        binding.rvDetailImage.addItemDecoration(SimpleImageDecoration(activity as CraftActivity))
    }

    private fun setCautionString() : String{
        var cautionString = ""
        for (i in 0 until(detailInfo.cautions.size)){
            cautionString += "\u2022 "
            cautionString += detailInfo.cautions[i]
            if (i != (detailInfo.cautions.size - 1)){
                cautionString += "\n"
            }
        }
        return cautionString
    }
}