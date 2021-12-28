package com.example.songil.page_artist

import android.os.Bundle
import android.util.Log
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.ArtistActivityBinding
import com.example.songil.page_artist.subpage_article.ArtistArticleFragment
import com.example.songil.page_artist.subpage_craft.ArtistCraftFragment
import com.example.songil.popup_sort.SortBottomSheet
import com.example.songil.popup_sort.SortBottomSheetSimple
import com.example.songil.popup_sort.popup_interface.PopupSortView

class ArtistActivity : BaseActivity<ArtistActivityBinding>(R.layout.artist_activity), PopupSortView {

    private val artistViewModel : ArtistViewModel by lazy { ViewModelProvider(this)[ArtistViewModel::class.java] }

    private val craftFragment : ArtistCraftFragment by lazy { ArtistCraftFragment() }
    private val articleFragment : ArtistArticleFragment by lazy { ArtistArticleFragment() }
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = artistViewModel
        binding.lifecycleOwner = this

        binding.layoutMainScroll.run {
            header = binding.layoutSticky
        }
        setObserver()
        setButton()

        val artistIdx = intent.getIntExtra("artistIdx", 1)
        artistViewModel.setArtistIdx(artistIdx)

        artistViewModel.tryGetArtistInfo()

        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, articleFragment).hide(articleFragment).add(binding.layoutFragment.id, craftFragment).commit()
        currentFragment = craftFragment

        setNestedScrollView()
    }

    private fun changeFragment(idx : Int){
        if (idx == 0){
            supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).hide(currentFragment).show(craftFragment).commit()
            currentFragment = craftFragment
            binding.tvSort.text = GlobalApplication.sort[artistViewModel.craftSort]
        } else {
            supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).hide(currentFragment).show(articleFragment).commit()
            currentFragment = articleFragment
            binding.tvSort.text = GlobalApplication.sort[artistViewModel.articleSort]
        }
    }

    private fun setButton(){
        binding.btnSort.setOnClickListener {
            val dialogFragment = if (currentFragment is ArtistCraftFragment){
                SortBottomSheet(this, artistViewModel.craftSort)
            } else {
                SortBottomSheetSimple(this, artistViewModel.articleSort)
            }
            dialogFragment.show(supportFragmentManager, dialogFragment.tag)
        }
    }

    private fun setObserver(){
        val artistInfoObserve = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    applyDataToView()
                }
            }
        }
        artistViewModel.artistInfoResult.observe(this, artistInfoObserve)

        val btnObserver = Observer<Boolean> { liveData ->
            if (liveData){
                changeFragment(0)
            } else {
                changeFragment(1)
            }
        }
        artistViewModel.btnCraftActivate.observe(this, btnObserver)
    }

    private fun setNestedScrollView(){
        binding.layoutMainScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight){
                (currentFragment as ArtistSubpageFragment).updateList()
            }
        })
    }

    private fun applyDataToView(){
        binding.tvArtistName.text = artistViewModel.artistInfo.name
        binding.tvStudio.text = artistViewModel.artistInfo.company
        binding.tvArtistComment.text = artistViewModel.artistInfo.introduction
        binding.tvMajorFieldValue.text = artistViewModel.artistInfo.major
        binding.tvBriefHistoryValue.text = artistViewModel.artistInfo.profile.joinToString("\n")
        binding.tvDisplayInformationValue.text = artistViewModel.artistInfo.exhibition.joinToString("\n")
        artistViewModel.artistInfo.imageUrl.let {
            Glide.with(this).load(it).into(binding.ivProfile)
        }
        binding.tvCraftCount.text = artistViewModel.artistInfo.totalCraftCnt.toString()
        binding.tvArticleCount.text = artistViewModel.artistInfo.totalArticleCnt.toString()
    }

    override fun sort(sort: String) {
        if (currentFragment is ArtistCraftFragment){
            artistViewModel.craftSort = sort
        } else {
            artistViewModel.articleSort = sort
        }
        binding.tvSort.text = GlobalApplication.sort[sort]
        (currentFragment as ArtistSubpageFragment).changeSort(sort)
    }

    fun getToolbarHeight() : Int {
        return binding.layoutSticky.height + binding.layoutAppbar.height
    }
}