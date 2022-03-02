package com.example.songil.page_search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.data.SearchItemCountList
import com.example.songil.databinding.SearchActivityBinding
import com.example.songil.page_search.models.SearchCategory
import com.example.songil.popup_sort.SortBottomSheetSimple
import com.example.songil.popup_sort.popup_interface.PopupSortView
import com.example.songil.recycler.adapter.ArticlePagingAdapter
import com.example.songil.recycler.adapter.Craft1PagingAdapter
import com.example.songil.recycler.adapter.PostPagingAdapter
import com.example.songil.recycler.adapter.RecentSearchAdapter
import com.example.songil.recycler.decoration.ArticleDecoration
import com.example.songil.recycler.decoration.Craft1Decoration
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchActivity : BaseActivity<SearchActivityBinding>(R.layout.search_activity), PopupSortView {

    private var isFirst = true
    private val viewModel : SearchViewModel by lazy { ViewModelProvider(this)[SearchViewModel::class.java] }

    private var shopPaging : Job ?= null
    private var withPaging : Job ?= null
    private var articlePaging : Job ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // 단순 text 설정
        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_search)

        showSearchLayout()
        binding.etSearchBar.requestFocus()
        setEditTextView()
        setButton()
        setRecyclerView()
        setObserver()

        // 우선, 여기에는 새로고침의 용도가 크게 의미가 없을 것 같아 비활성화
        binding.layoutRefreshShop.isEnabled = false
        binding.layoutRefreshWith.isEnabled = false
        binding.layoutRefreshArticle.isEnabled = false

        binding.tvSort.text = GlobalApplication.sort[viewModel.sort] ?: "인기순"
    }

    private fun setEditTextView(){
        binding.etSearchBar.setOnClickListener {
            showSearchLayout()
        }

        binding.etSearchBar.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)

                if (binding.etSearchBar.text.toString().replace(" ", "") == ""){
                    showSimpleToastMessage(getString(R.string.empty_input_text))
                } else {
                    hideSearchLayout()
                    changeCategory(SearchCategory.SHOP)
                    viewModel.tryGetSearchPageCnt()
                }


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

        binding.btnWith.setOnClickListener {
            changeCategory(SearchCategory.WITH)
            viewModel.tryGetSearchPageCnt()
        }

        binding.btnShop.setOnClickListener {
            changeCategory(SearchCategory.SHOP)
            viewModel.tryGetSearchPageCnt()
        }

        binding.btnArticle.setOnClickListener {
            changeCategory(SearchCategory.ARTICLE)
            viewModel.tryGetSearchPageCnt()
        }

        binding.btnSort.setOnClickListener {
            val dialog = SortBottomSheetSimple(this, viewModel.sort)
            dialog.show(supportFragmentManager, dialog.tag)
        }

        binding.btnRemoveEdittext.setOnClickListener {
            binding.etSearchBar.setText("")
        }
    }

    private fun setRecyclerView(){
        binding.rvRecentSearch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvRecentSearch.adapter = RecentSearchAdapter(::searchByWord, ::deleteWord)

        binding.rvContentShop.layoutManager = GridLayoutManager(this, 2)
        binding.rvContentShop.adapter = Craft1PagingAdapter()
        binding.rvContentShop.addItemDecoration(Craft1Decoration(this))

        binding.rvContentWith.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContentWith.adapter = PostPagingAdapter()

        binding.rvContentArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false )
        binding.rvContentArticle.adapter = ArticlePagingAdapter()
        binding.rvContentArticle.addItemDecoration(ArticleDecoration(this))
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

        val startIdxObserver = Observer<Int> { startIdx ->
            when {
                startIdx == 0 -> { // 검색 결과가 없을 경우
                    showEmptyView()
                    restartPagingJob()
                }
                startIdx < 0 -> { // 아무것도 입력하지 않은 경우
                    showSimpleToastMessage(getString(R.string.empty_input_text))
                }
                else -> { // 검색 결과가 있을 경우
                    restartPagingJob()
                }
            }
        }
        viewModel.startIdx.observe(this, startIdxObserver)

        val cntListObserver = Observer<SearchItemCountList> { liveData ->
            binding.tvShopCount.text = liveData.shopCnt.toString()
            binding.tvArticleCount.text = liveData.articleCnt.toString()
            binding.tvWithCount.text = liveData.withCnt.toString()
            when (viewModel.searchCategory){
                SearchCategory.ARTICLE -> {
                    binding.tvSearchresultCount.text = getString(R.string.form_search_result, liveData.articleCnt)
                }
                SearchCategory.WITH -> {
                    binding.tvSearchresultCount.text = getString(R.string.form_search_result, liveData.withCnt)
                }
                SearchCategory.SHOP -> {
                    binding.tvSearchresultCount.text = getString(R.string.form_search_result, liveData.shopCnt)
                }
            }
        }
        viewModel.cntList.observe(this, cntListObserver)
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

    private fun showEmptyView(){
        binding.viewEmpty.root.visibility = View.VISIBLE
    }

    private fun changeCategory(searchCategory : SearchCategory){
        viewModel.changeCategory(searchCategory)
        binding.viewEmpty.root.visibility = View.GONE
        when (searchCategory){
            SearchCategory.SHOP -> {
                binding.rvContentShop.visibility = View.VISIBLE
                binding.rvContentWith.visibility = View.GONE
                binding.rvContentArticle.visibility = View.GONE
            }
            SearchCategory.WITH -> {
                binding.rvContentShop.visibility = View.GONE
                binding.rvContentWith.visibility = View.VISIBLE
                binding.rvContentArticle.visibility = View.GONE
            }
            SearchCategory.ARTICLE -> {
                binding.rvContentShop.visibility = View.GONE
                binding.rvContentWith.visibility = View.GONE
                binding.rvContentArticle.visibility = View.VISIBLE
            }
        }
    }

    private fun restartPagingJob(){
        val searchCategory = viewModel.searchCategory
        shopPaging?.cancel()
        withPaging?.cancel()
        articlePaging?.cancel()
        when (searchCategory){
            SearchCategory.SHOP -> {
                shopPaging = lifecycleScope.launch {
                    (binding.rvContentShop.adapter as Craft1PagingAdapter).submitData(PagingData.empty())
                    viewModel.shopPagingFlow.collectLatest { pagingData ->
                        (binding.rvContentShop.adapter as Craft1PagingAdapter).submitData(pagingData)
                    }
                }
            }
            SearchCategory.WITH -> {
                withPaging = lifecycleScope.launch {
                    (binding.rvContentWith.adapter as PostPagingAdapter).submitData(PagingData.empty())
                    viewModel.withPagingFlow.collectLatest { pagingData ->
                        (binding.rvContentWith.adapter as PostPagingAdapter).submitData(pagingData)
                    }
                }
            }
            SearchCategory.ARTICLE -> {
                shopPaging = lifecycleScope.launch {
                    (binding.rvContentArticle.adapter as ArticlePagingAdapter).submitData(PagingData.empty())
                    viewModel.articlePagingFlow.collectLatest { pagingData ->
                        (binding.rvContentArticle.adapter as ArticlePagingAdapter).submitData(pagingData)
                    }
                }
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
        changeCategory(SearchCategory.SHOP)
        viewModel.tryGetSearchPageCnt()
    }

    override fun sort(sort: String) {
        viewModel.sort = sort
        binding.tvSort.text = GlobalApplication.sort[sort] ?: "인기순"
        viewModel.tryGetSearchPageCnt()
    }
}