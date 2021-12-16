package com.example.songil.page_search.subpage_searching

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SearchFragmentSearchingBinding
import com.example.songil.page_search.SearchActivity
import com.example.songil.recycler.adapter.RecentSearchAdapter
import com.example.songil.recycler.rv_interface.RvTriggerView
import com.google.android.material.chip.Chip

class SearchingFragment : BaseFragment<SearchFragmentSearchingBinding>(SearchFragmentSearchingBinding::bind, R.layout.search_fragment_searching), RvTriggerView {

    private lateinit var viewModel: SearchingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SearchingViewModel::class.java]

        setRecyclerView()
        setObserver()

        viewModel.tempGetPopular()
        viewModel.tempGetRecent()
    }

    private fun setObserver(){
        val popularObserver = Observer<ArrayList<String>>{ liveData ->
            for (data in liveData){
                val chip = Chip(context)
                chip.text = data
                chip.setChipBackgroundColorResource(R.color.g_1)
                chip.setTextColor(ContextCompat.getColor(context as SearchActivity, R.color.songil_2))
                chip.isCheckable = false
                chip.setOnClickListener {

                }
                binding.cgPopularSearch.addView(chip)
            }
        }
        viewModel.popularSearch.observe(viewLifecycleOwner, popularObserver)

        val recentObserver = Observer<Int>{ liveData ->
            when (liveData){
                -1 -> {
                    (binding.rvRecentSearch.adapter as RecentSearchAdapter).applyData(viewModel.recentSearch)
                }
                else -> {
                    (binding.rvRecentSearch.adapter as RecentSearchAdapter).notifyDataSetChanged()//notifyItemRemoved(liveData)
                }
            }
        }
        viewModel.changeRecentSearch.observe(viewLifecycleOwner, recentObserver)
    }

    private fun setRecyclerView(){
        binding.rvRecentSearch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvRecentSearch.adapter = RecentSearchAdapter(context as Activity, this)
    }

    override fun notifyDataChange(type: Int, position: Int?) {
        when (type){
            0 -> { // remove item
                viewModel.removeRecentWord(position!!)
            }
            1 -> {

            }
        }
    }
}