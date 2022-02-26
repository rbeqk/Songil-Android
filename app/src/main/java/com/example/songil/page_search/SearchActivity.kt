package com.example.songil.page_search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.SearchActivityBinding
import com.example.songil.recycler.adapter.RecentSearchAdapter
import com.google.android.material.chip.Chip

class SearchActivity : BaseActivity<SearchActivityBinding>(R.layout.search_activity) {

    private var isFirst = true
    private val viewModel : SearchViewModel by lazy { ViewModelProvider(this)[SearchViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showSearchLayout()
        binding.etSearchBar.requestFocus()
        setEditTextView()
        setButton()
        setRecyclerView()
        setObserver()
    }

    private fun setEditTextView(){
        binding.etSearchBar.setOnClickListener {
            showSearchLayout()
        }

        binding.etSearchBar.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                Log.d("test", v.text.toString())
                hideSearchLayout()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
           onBackPressed()
        }

        binding.tvbtnRemoveAll.setOnClickListener {
            viewModel.tryRemoveAllRecentlyKeywords()
        }

        binding.btnShop.setOnClickListener {

        }

        binding.btnShop.setOnClickListener {

        }

        binding.btnArticle.setOnClickListener {

        }
    }

    private fun setRecyclerView(){
        binding.rvRecentSearch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvRecentSearch.adapter = RecentSearchAdapter(::searchByWord, ::deleteWord)
    }

    private fun setObserver(){
        val getKeywordsObserver = Observer<Int> { resultCode ->
            when (resultCode){
                200 -> {
                    applyKeywords()
                }
            }
        }
        viewModel.getKeywordsResult.observe(this, getKeywordsObserver)

        val removeKeywordsObserver = Observer<Int> { resultCode ->
            when (resultCode){
                200 -> {
                    viewModel.tryGetSearchKeywords()
                }
                3000 -> { // jwt 기한 만료

                }
            }
        }
        viewModel.removeResult.observe(this, removeKeywordsObserver)
    }

    override fun onBackPressed() {
        when {
            isFirst -> {
                super.onBackPressed()
            }
            binding.layoutSearchSearching.visibility == View.VISIBLE -> {
                hideSearchLayout()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    private fun showSearchLayout(){
        viewModel.tryGetSearchKeywords()
        binding.layoutSearchSearching.visibility = View.VISIBLE
    }

    private fun hideSearchLayout(){
        isFirst = false
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(binding.etSearchBar.windowToken, 0)
        binding.layoutSearchSearching.visibility = View.GONE
    }

    private fun applyKeywords(){
        if (viewModel.recentlyKeywords.size == 0){
            binding.rvRecentSearch.visibility = View.GONE
            binding.tvNoRecentlyKeywords.visibility = View.VISIBLE
        } else {
            binding.rvRecentSearch.visibility = View.VISIBLE
            binding.tvNoRecentlyKeywords.visibility = View.GONE
        }
        (binding.rvRecentSearch.adapter as RecentSearchAdapter).applyData(viewModel.recentlyKeywords)


        while (binding.cgPopularSearch.childCount > 0){
            val view = binding.cgPopularSearch.getChildAt(0)
            binding.cgPopularSearch.removeView(view)
        }

        for (data in viewModel.popularKeywords){
            val chip = Chip(this)
            chip.text = data
            chip.setChipBackgroundColorResource(R.color.g_1)
            chip.setTextColor(ContextCompat.getColor(this, R.color.songil_2))
            chip.isCheckable = false
            chip.setOnClickListener {
                binding.etSearchBar.setText(data)
                hideSearchLayout()
                binding.etSearchBar.clearFocus()
            }
            binding.cgPopularSearch.addView(chip)
        }
    }

    private fun deleteWord(targetWord : String){
        viewModel.tryRemoveWord(targetWord)
    }

    private fun searchByWord(targetWord : String){
        binding.etSearchBar.setText(targetWord)
        hideSearchLayout()
        binding.etSearchBar.clearFocus()
    }
}