package com.example.songil.page_article

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.songil.R
import com.example.songil.viewPager2.adapter.Vp2ArticleTitleAdapter
import com.example.songil.recycler.decoration.Vp2ArticleDecoration
import com.example.songil.config.BaseFragment
import com.example.songil.data.SimpleArticle
import com.example.songil.databinding.ArticleFragmentMainBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.utils.dpToPx
import kotlin.math.abs

class ArticleFragmentMain : BaseFragment<ArticleFragmentMainBinding>(ArticleFragmentMainBinding::bind, R.layout.article_fragment_main) {

    private lateinit var viewModel: ArticleViewModel
    private var isUser = false

    private var pageRangeSize = 1
    private var progressMax = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ArticleViewModel::class.java]

        setViewPager()
        setSeekBar()

        val articleObserver = Observer<ArrayList<SimpleArticle>>{ LiveData ->
            (binding.vp2Article.adapter as Vp2ArticleTitleAdapter).applyData(LiveData)
            pageRangeSize = (progressMax / (LiveData.size - 1))
        }

        viewModel.articleData.observe(viewLifecycleOwner, articleObserver)
        //viewModel.tryGetArticleData()
        viewModel.getArticleData()
    }

    private fun setViewPager(){
        val nextItemVisiblePx = dpToPx(activity as MainActivity, 16)
        val currentItemHorizontalMarginPx = dpToPx(activity as MainActivity, 32)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        binding.vp2Article.adapter = Vp2ArticleTitleAdapter(activity as MainActivity)
        binding.vp2Article.setPageTransformer { page, position ->
            page.translationX = -1  * pageTranslationX * position
            page.scaleY = 1 - (0.1f * abs(position))
        }
        binding.vp2Article.addItemDecoration(Vp2ArticleDecoration(activity as MainActivity))
        binding.vp2Article.offscreenPageLimit = 3
        binding.vp2Article.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (!isUser){
                    binding.seekArticle.setProgress(position * pageRangeSize, true)
                }
            }
        })
    }

    private fun setSeekBar(){
        progressMax = binding.seekArticle.max
        binding.seekArticle.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    isUser = true
                    binding.vp2Article.currentItem = (seekBar!!.progress) / pageRangeSize
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
/*                val threshold = (seekBar!!.progress / pageRangeSize) * pageRangeSize
                binding.seekArticle.setProgress(threshold, true)*/
                isUser = false
            }

        })
    }
}