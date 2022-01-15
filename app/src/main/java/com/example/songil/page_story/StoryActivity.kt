package com.example.songil.page_story

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.StoryActivityBinding
import com.example.songil.page_story.subpage_story_chat.StoryChatActivity
import com.example.songil.viewPager2.adapter.Vp2ImageAdapter
import com.google.android.material.chip.Chip

class StoryActivity : BaseActivity<StoryActivityBinding>(R.layout.story_activity){

    private val viewModel : StoryViewModel by lazy { ViewModelProvider(this)[StoryViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storyIdx = intent.getIntExtra(GlobalApplication.STORY_IDX, 1)

        setButton()
        setObserver()
        viewModel.tryGetStoryDetail(storyIdx)
    }

    private fun setObserver(){
        val getStoryObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    applyView()
                }
            }
        }
        viewModel.storyDetailResult.observe(this, getStoryObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnComment.setOnClickListener {
            val intent = Intent(this, StoryChatActivity::class.java)
            intent.putExtra(GlobalApplication.STORY_IDX, viewModel.storyIdx)
            startActivityHorizontal(intent)
        }
    }

    private fun applyView(){
        val storyData = viewModel.storyDetail
        setViewPager(storyData.imageUrl)
        binding.tvNavigation.text = getString(R.string.story_title, storyData.userName)
        binding.tvStoryTitle.text = storyData.title
        binding.tvWriterName.text = storyData.userName
        binding.tvUploadDate.text = storyData.createdAt
        binding.tvStoryContent.text = storyData.content
        Glide.with(this).load(storyData.userProfile).fallback(R.drawable.ic_with_full_gray_32).into(binding.ivWriterThumbnail)
        setFavBtn(storyData.isLike, storyData.totalLikeCount)
        binding.tvCommentCount.text = storyData.totalCommentCnt.toString()
        setChip(storyData.tag)
        if (storyData.imageUrl.size <= 1) { binding.tvImageCnt.visibility = View.INVISIBLE }
        else { binding.tvImageCnt.text = getString(R.string.form_count, 1, storyData.tag.size)}
    }

    private fun setViewPager(imageList : ArrayList<String>){
        binding.vp2Thumbnail.adapter = Vp2ImageAdapter(this, imageList)
        binding.vp2Thumbnail.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvImageCnt.text = getString(R.string.form_count, position + 1, imageList.size)
            }
        })
    }

    private fun setFavBtn(isLike : String, likeCount : Int){
        if (isLike == "Y"){
            binding.btnFavorite.setImageResource(R.drawable.ic_heart_base_16)
            binding.btnFavorite.setOnClickListener {
                setFavBtn("N", likeCount - 1)
            }
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_heart_line_16)
            binding.btnFavorite.setOnClickListener {
                setFavBtn("Y", likeCount + 1)
            }
        }
        binding.tvFavoriteCount.text = likeCount.toString()
    }

    private fun setChip(tags : ArrayList<String>){
        for (tag in tags){
            val chip = Chip(this)
            chip.text = tag
            chip.setChipBackgroundColorResource(R.color.g_1)
            chip.setTextColor(ContextCompat.getColor(this, R.color.songil_2))
            chip.isCheckable = false
            chip.setOnClickListener {

            }
            binding.cgTag.addView(chip)
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}