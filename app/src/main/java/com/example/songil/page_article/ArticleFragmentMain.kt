package com.example.songil.page_article

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.adapter.ArticleViewPager2Adapter
import com.example.songil.adapter.ArticleViewPager2ItemDecoration
import com.example.songil.config.BaseFragment
import com.example.songil.data.Article
import com.example.songil.databinding.ArticleFragmentMainBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.utils.dpToPx
import kotlin.math.abs

class ArticleFragmentMain : BaseFragment<ArticleFragmentMainBinding>(ArticleFragmentMainBinding::bind, R.layout.article_fragment_main) {

    private lateinit var viewModel: ArticleViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ArticleViewModel::class.java]

        val nextItemVisiblePx = dpToPx(activity as MainActivity, 14)
        val currentItemHorizontalMarginPx = dpToPx(activity as MainActivity, 34)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        binding.vp2Article.adapter = ArticleViewPager2Adapter(activity as MainActivity)
        binding.vp2Article.setPageTransformer { page, position ->
            page.translationX = -1  * pageTranslationX * position
            page.scaleY = 1 - (0.1f * abs(position))
        }
        binding.vp2Article.addItemDecoration(ArticleViewPager2ItemDecoration(activity as MainActivity))
        binding.vp2Article.offscreenPageLimit = 3

        val articleObserver = Observer<ArrayList<Article>>{ LiveData ->
            (binding.vp2Article.adapter as ArticleViewPager2Adapter).applyData(LiveData)
        }

        viewModel.articleData.observe(viewLifecycleOwner, articleObserver)
        viewModel.getArticleData()
    }
}