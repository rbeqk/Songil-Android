package com.songil.songil.page_craft.subpage_detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseFragment
import com.songil.songil.data.CraftDetailInfo
import com.songil.songil.databinding.CraftFragmentDetailBinding
import com.songil.songil.page_craft.CraftActivity
import com.songil.songil.recycler.adapter.SimpleImageAdapter
import com.songil.songil.recycler.decoration.SimpleImageDecoration

class CraftFragmentDetail : BaseFragment<CraftFragmentDetailBinding>(CraftFragmentDetailBinding::bind, R.layout.craft_fragment_detail) {

    //var tempFlag = true
    private var detailInfo : CraftDetailInfo?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvDetailImage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvDetailImage.adapter = SimpleImageAdapter(activity as CraftActivity, arrayListOf())
        binding.rvDetailImage.addItemDecoration(SimpleImageDecoration(activity as CraftActivity))
    }

    private fun applyView(){
        detailInfo?.let { detailInfo ->
            binding.tvCraftDescriptionContent.text = detailInfo.content
            binding.tvSizeValue.text = detailInfo.size
            binding.tvWarningNoticeContent.text = setCautionString(detailInfo.cautions)
            (binding.rvDetailImage.adapter as SimpleImageAdapter).applyData(detailInfo.detailImageUrls)
        }
    }

    private fun setCautionString(cautions : ArrayList<String>) : String{
        var cautionString = ""
        for (i in 0 until(cautions.size)){
            cautionString += "\u2022 "
            cautionString += cautions[i]
            if (i != (cautions.size - 1)){
                cautionString += "\n"
            }
        }
        return cautionString
    }

    fun applyData(inputArtistInfo : CraftDetailInfo){
        detailInfo = inputArtistInfo
        applyView()
    }
}