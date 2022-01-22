package com.example.songil.page_qna

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.config.ReportTarget
import com.example.songil.databinding.ChatActivityBinding
import com.example.songil.page_report.ReportActivity
import com.example.songil.recycler.adapter.PostAndChatAdapter
import com.example.songil.recycler.rv_interface.RvPostAndChatView
import com.example.songil.utils.softKeyboardCallback.KeyboardVisibilityUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class QnaActivity : BaseActivity<ChatActivityBinding>(R.layout.chat_activity), RvPostAndChatView {

    private val viewModel : QnaViewModel by lazy { ViewModelProvider(this)[QnaViewModel::class.java] }
    private lateinit var keyboardVisibilityUtils : KeyboardVisibilityUtils

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
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.tryWriteComment(binding.etComment.text.toString())
            binding.btnRegister.isClickable = false
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
}