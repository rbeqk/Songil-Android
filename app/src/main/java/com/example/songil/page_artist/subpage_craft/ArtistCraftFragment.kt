package com.example.songil.page_artist.subpage_craft

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SortRecyclerviewFragmentBinding

class ArtistCraftFragment : BaseFragment<SortRecyclerviewFragmentBinding>(SortRecyclerviewFragmentBinding::bind, R.layout.sort_recyclerview_fragment){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }
}