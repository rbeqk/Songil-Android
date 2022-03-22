package com.songil.songil.page_story

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.songil.songil.R
import com.songil.songil.config.*
import com.songil.songil.databinding.StoryActivityBinding
import com.songil.songil.page_report.ReportActivity
import com.songil.songil.page_story.subpage_story_chat.StoryChatActivity
import com.songil.songil.page_storywrite.StoryWriteActivity
import com.songil.songil.popup_more.MoreBottomSheet
import com.songil.songil.popup_more.popup_interface.PopupMoreView
import com.songil.songil.popup_remove.RemoveDialog
import com.songil.songil.popup_remove.popup_interface.PopupRemoveView
import com.songil.songil.popup_warning.SocketTimeoutDialog
import com.songil.songil.popup_warning.WarningDialog
import com.songil.songil.viewPager2.adapter.Vp2ImageAdapter
import com.google.android.material.chip.Chip

class StoryActivity : BaseActivity<StoryActivityBinding>(R.layout.story_activity), PopupMoreView, PopupRemoveView {

    private val viewModel : StoryViewModel by lazy { ViewModelProvider(this)[StoryViewModel::class.java] }
    private lateinit var writeResult : ActivityResultLauncher<Intent>
    private var itemIsExists = true // 현재 조회중인 아이템이 존재하는지 여부
    private var itemIsLoaded = false // 현재 조회하려는 story 요청에 대한 서버의 응답을 받았는지 여부

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storyIdx = intent.getIntExtra(GlobalApplication.STORY_IDX, 1)
        viewModel.storyIdx = storyIdx

        writeResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                viewModel.tryGetStoryDetail()
            }
        }

        setButton()
        setObserver()
        viewModel.tryGetStoryDetail()
    }

    private fun setObserver(){
        val getStoryObserver = Observer<Int>{ liveData ->
            itemIsLoaded = true
            when (liveData){
                200 -> {
                    applyView()
                }
                2311 -> {
                    val dialog = WarningDialog("삭제된 스토리입니다.", "이전 페이지로 이동되며\n바로 새로고침됩니다."){
                        itemIsExists = false
                        finish()
                    }
                    dialog.show(supportFragmentManager, dialog.tag)
                }
            }
        }
        viewModel.storyDetailResult.observe(this, getStoryObserver)

        val patchLikeObserver = Observer<Boolean>{ liveData ->
            if (liveData){
                applyLikeData()
            }
            binding.btnFavorite.isClickable = true
        }
        viewModel.likeResult.observe(this, patchLikeObserver)

        val removeStoryObserver = Observer<Boolean>{ liveData ->
            if (liveData){
                finish()
            }
        }
        viewModel.removeResult.observe(this, removeStoryObserver)

        val networkErrorObserver = Observer<BaseViewModel.FetchState>{ _ ->
            itemIsExists = false
            val dialog = SocketTimeoutDialog(true)
            dialog.show(supportFragmentManager, dialog.tag)
        }
        viewModel.fetchState.observe(this, networkErrorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnComment.setOnClickListener {
            val intent = Intent(this, StoryChatActivity::class.java)
            intent.putExtra(GlobalApplication.STORY_IDX, viewModel.storyIdx)
            startActivityHorizontal(intent)
        }

        binding.btnFavorite.setOnClickListener {
            if (GlobalApplication.checkIsLogin()){
                viewModel.tryToggleLike()
                binding.btnFavorite.isClickable = false
            } else {
                callNeedLoginDialog()
            }
        }

        binding.btnMore.setOnClickListener {
            val moreBottomSheet = MoreBottomSheet(this, (viewModel.storyDetail.isUserStory == "Y"))
            moreBottomSheet.show(supportFragmentManager, moreBottomSheet.tag)
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
        applyLikeData()
        binding.tvCommentCount.text = storyData.totalCommentCnt.toString()
        setChip(storyData.tag)
        binding.tvImageCnt.text = getString(R.string.form_count, 1, storyData.imageUrl.size)
        if (storyData.imageUrl.size <= 1) { binding.tvImageCnt.visibility = View.INVISIBLE }
        else {
            binding.tvImageCnt.visibility = View.VISIBLE
        }
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

    private fun applyLikeData(){
        if (viewModel.storyDetail.isLike == "Y"){
            binding.ivFavorite.setImageResource(R.drawable.ic_heart_base_16)
        } else {
            binding.ivFavorite.setImageResource(R.drawable.ic_heart_line_16)
        }
        binding.tvFavoriteCount.text = viewModel.storyDetail.totalLikeCnt.toString()
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
        if (itemIsExists && itemIsLoaded){
            val intent = Intent(this, BaseActivity::class.java)
            intent.putExtra("STORY", viewModel.getFrontStory()) // error check
            setResult(RESULT_OK, intent)
        } else if (!itemIsExists && itemIsLoaded) {
            val intent = Intent(this, BaseActivity::class.java)
            setResult(RESULT_OK, intent)
        }
        super.finish()
        exitHorizontal
    }

    override fun bottomSheetModifyClick() {
        val intent = Intent(this, StoryWriteActivity::class.java)
        intent.putExtra(GlobalApplication.WRITE_TYPE, WriteType.MODIFY)
        intent.putExtra(GlobalApplication.TARGET_IDX, viewModel.storyIdx)
        writeResult.launch(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }

    override fun bottomSheetRemoveClick() {
        val dialog = RemoveDialog(this)
        dialog.show(supportFragmentManager, dialog.tag)
    }

    override fun bottomSheetReportClick() {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra(GlobalApplication.REPORT_TARGET, ReportTarget.STORY)
        intent.putExtra(GlobalApplication.TARGET_IDX, viewModel.storyIdx)
        startActivityHorizontal(intent)
    }

    override fun popupRemoveClick() {
        viewModel.tryDeleteStory()
    }

}