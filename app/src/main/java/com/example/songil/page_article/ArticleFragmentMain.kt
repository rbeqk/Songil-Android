package com.example.songil.page_article

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.songil.R
import com.example.songil.viewPager2.adapter.Vp2ArticleTitleAdapter
import com.example.songil.viewPager2.decoration.Vp2ArticleDecoration
import com.example.songil.config.BaseFragment
import com.example.songil.config.GlobalApplication
import com.example.songil.data.SimpleArticle
import com.example.songil.databinding.ArticleFragmentMainBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.page_search.SearchActivity
import com.example.songil.page_search.models.SearchCategory
import com.example.songil.popup_warning.SocketTimeoutDialog
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
        setButton()
        setObserver()

        viewModel.tryGetArticleData()
    }

    private fun setButton(){
        binding.btnSearch.setOnClickListener {
            val intent = Intent(activity as MainActivity, SearchActivity::class.java)
            intent.putExtra(GlobalApplication.SEARCH_CATEGORY, SearchCategory.ARTICLE)
            (activity as MainActivity).startActivityHorizontal(intent)
        }
    }

    private fun setObserver(){
        val articleObserver = Observer<ArrayList<SimpleArticle>>{ LiveData ->
            (binding.vp2Article.adapter as Vp2ArticleTitleAdapter).applyData(LiveData)
            pageRangeSize = (progressMax / (LiveData.size - 1))
            changeThumbSize(LiveData.size)
        }
        viewModel.articleData.observe(viewLifecycleOwner, articleObserver)

        val articleResultCodeObserver = Observer<Boolean>{ LiveData ->
            if (LiveData){
                val socketTimeoutDialog = SocketTimeoutDialog()
                socketTimeoutDialog.show(childFragmentManager, socketTimeoutDialog.tag)
            }
        }
        viewModel.isSocketTimeout.observe(viewLifecycleOwner, articleResultCodeObserver)
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
                    binding.seekArticle.setProgress(position * pageRangeSize, false)
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
                isUser = false
            }
        })
    }

    private fun changeThumbSize(itemCount : Int){
        if (itemCount != 0) {
            val thumbWidth = (getWindowSize()[0] - dpToPx(requireContext(), 60)) / itemCount

            val thumb = ShapeDrawable(RoundRectShape(listOf(2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f).toFloatArray(), null, null))
            thumb.intrinsicHeight = dpToPx(requireContext(), 4)
            thumb.intrinsicWidth = thumbWidth
            thumb.paint.color = Color.BLACK
            binding.seekArticle.thumb = thumb

            val seekBarParams = binding.seekArticle.layoutParams as ConstraintLayout.LayoutParams
            seekBarParams.setMargins(thumbWidth / 2, dpToPx(requireContext(), 48),  thumbWidth / 2 , 0)
            binding.seekArticle.layoutParams = seekBarParams
        }
    }
}