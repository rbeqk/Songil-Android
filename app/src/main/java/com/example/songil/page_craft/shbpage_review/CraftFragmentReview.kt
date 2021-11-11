package com.example.songil.page_craft.shbpage_review

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.CraftFragmentReviewBinding
import com.example.songil.page_craft.CraftActivity
import com.example.songil.page_craft.models.CraftReview
import com.example.songil.recycler.adapter.RvReviewAdapter
import com.example.songil.recycler.decoration.CraftReviewDecoration

class CraftFragmentReview(private val reviewList : ArrayList<CraftReview>) : BaseFragment<CraftFragmentReviewBinding>(CraftFragmentReviewBinding::bind, R.layout.craft_fragment_review) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvReview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter = RvReviewAdapter(activity as CraftActivity, reviewList)
        binding.rvReview.addItemDecoration(CraftReviewDecoration(activity as CraftActivity))
    }
}