package com.example.songil.page_qna

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.config.ReportTarget
import com.example.songil.config.WriteType
import com.example.songil.databinding.ChatActivityBinding
import com.example.songil.page_qnawrite.QnaWriteActivity
import com.example.songil.page_report.ReportActivity
import com.example.songil.popup_more.MoreBottomSheet
import com.example.songil.popup_more.popup_interface.PopupMoreView
import com.example.songil.popup_remove.RemoveDialog
import com.example.songil.popup_remove.popup_interface.PopupRemoveView
import com.example.songil.recycler.adapter.PostAndChatAdapter
import com.example.songil.recycler.rv_interface.RvPostAndChatView
import com.example.songil.utils.softKeyboardCallback.KeyboardVisibilityUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class QnaActivity : BaseActivity<ChatActivityBinding>(R.layout.chat_activity), RvPostAndChatView, PopupMoreView, PopupRemoveView {

    private val viewModel : QnaViewModel by lazy { ViewModelProvider(this)[QnaViewModel::class.java] }
    private lateinit var keyboardVisibilityUtils : KeyboardVisibilityUtils
    private lateinit var writeResult : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.qnaIdx = intent.getIntExtra(GlobalApplication.QNA_IDX, 1)

        binding.layoutRefresh.setOnRefreshListener {
            (binding.rvComment.adapter as PostAndChatAdapter).refresh()
            binding.layoutRefresh.isRefreshing = false
        }

        setRecyclerView()
        setObserver()
        setButton()

        writeResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                viewModel.getQna()
            }
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvComment.adapter as PostAndChatAdapter).submitData(pagingData)
            }
        }

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window, onShowKeyboard = {

        }, onHideKeyboard = {
            (binding.rvComment.adapter as PostAndChatAdapter).setPointPosition(null)
            viewModel.replyPointIdx = null
            binding.etComment.hint = ""
        })

        viewModel.getQna()
    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }

    private fun setRecyclerView(){
        binding.rvComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvComment.adapter = PostAndChatAdapter(this)
    }

    private fun setObserver(){
        val qnaResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                }
            }
            binding.btnRegister.isClickable = true
        }
        viewModel.loadQna.observe(this, qnaResult)

        val commentRegisterResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                    binding.etComment.setText("")
                    (binding.rvComment.adapter as PostAndChatAdapter).setPointPosition(null)
                    viewModel.replyPointIdx = null
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.etComment.windowToken, 0)
                }
            }
            binding.btnRegister.isClickable = true
        }
        viewModel.commentResult.observe(this, commentRegisterResult)

        val removeCommentResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                }
            }
        }
        viewModel.deleteCommentResult.observe(this, removeCommentResult)

        val changeLikeObserver = Observer<Boolean>{ liveData ->
            if (liveData){
                if ((binding.rvComment.adapter as PostAndChatAdapter).itemCount > 0) {
                    (binding.rvComment.adapter as PostAndChatAdapter).notifyItemChanged(0, "like")
                }
            }
        }
        viewModel.changeLikeResult.observe(this, changeLikeObserver)

        val removeQnaResult = Observer<Boolean> { liveData ->
            if (liveData){
                finish()
            } else {
                Log.d("QnaActivity", "remove qna on failure")
            }
        }
        viewModel.deleteQnaResult.observe(this, removeQnaResult)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.tryWriteComment(binding.etComment.text.toString())
            binding.btnRegister.isClickable = false
        }

        binding.btnMore.setOnClickListener {
            val moreBottomSheet = MoreBottomSheet(this, (viewModel.getIsWriter()))
            moreBottomSheet.show(supportFragmentManager, moreBottomSheet.tag)
        }
    }

    override fun onBackPressed() {
        if ((binding.rvComment.adapter as PostAndChatAdapter).getPointPosition() != null){
            (binding.rvComment.adapter as PostAndChatAdapter).setPointPosition(null)
        } else {
            super.onBackPressed()
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    override fun clickReply(parentIdx: Int, userName : String?) {
        if (viewModel.replyPointIdx == parentIdx){
            viewModel.replyPointIdx = null
            binding.etComment.hint = ""
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etComment.windowToken, 0)
        }
        else {
            binding.etComment.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.etComment, 0)
            viewModel.replyPointIdx = parentIdx
            userName?.let { binding.etComment.hint = getString(R.string.reply_to, it) }
        }
    }

    override fun removeChat(commentIdx: Int) {
        viewModel.tryRemoveComment(commentIdx)
    }

    override fun reportChat(commentIdx: Int) {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra(GlobalApplication.TARGET_IDX, commentIdx)
        intent.putExtra(GlobalApplication.REPORT_TARGET, ReportTarget.QNA_COMMENT)
        startActivityHorizontal(intent)
    }

    override fun clickLikeBtn() {
        viewModel.tryToggleLike()
    }

    override fun vote(abTestIdx: Int, vote: String) { /* empty function, only ab-test activity use this function */ }

    override fun cancelVote(abTestIdx: Int) { /* empty function, only ab-test activity use this function */ }

    override fun bottomSheetModifyClick() {
        val intent = Intent(this, QnaWriteActivity::class.java)
        intent.putExtra(GlobalApplication.WRITE_TYPE, WriteType.MODIFY)
        intent.putExtra(GlobalApplication.TARGET_IDX, viewModel.qnaIdx)
        writeResult.launch(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }

    override fun bottomSheetRemoveClick() {
        val dialog = RemoveDialog(this)
        dialog.show(supportFragmentManager, dialog.tag)
    }

    override fun bottomSheetReportClick() {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra(GlobalApplication.REPORT_TARGET, ReportTarget.QNA)
        intent.putExtra(GlobalApplication.TARGET_IDX, viewModel.qnaIdx)
        startActivityHorizontal(intent)
    }

    override fun popupRemoveClick() {
        viewModel.tryRemoveQna()
    }
}