package com.example.songil.page_abtest

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

// Qna activity 와 99% 동일함, 나중에 리팩토링시 통합하는 작업 수행 예정
class AbtestActivity : BaseActivity<ChatActivityBinding>(R.layout.chat_activity), RvPostAndChatView {

    private val viewModel : AbtestViewModel by lazy { ViewModelProvider(this)[AbtestViewModel::class.java] }
    private lateinit var keyboardVisibilityUtils : KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.abtestIdx = intent.getIntExtra(GlobalApplication.ABTEST_IDX, -1)

        binding.layoutRefresh.setOnRefreshListener {
            (binding.rvComment.adapter as PostAndChatAdapter).refresh()
            binding.layoutRefresh.isRefreshing = false
        }

        setRecyclerView()
        setButton()
        setObserver()

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


        viewModel.tryGetAbtest()
    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }

    private fun setRecyclerView(){
        binding.rvComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvComment.adapter = PostAndChatAdapter(this)
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

    private fun setObserver(){
        val abTestResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    binding.tvTitle.text = getString(R.string.AB_TEST_title, viewModel.getAbtest.artistName)
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                }
            }
            binding.btnRegister.isClickable = true
        }
        viewModel.loadAbtest.observe(this, abTestResult)

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

        val deleteCommentResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                }
            }
        }
        viewModel.deleteCommentResult.observe(this, deleteCommentResult)
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
        viewModel.tryDeleteComment(commentIdx)
    }

    override fun reportChat(commentIdx: Int) {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra(GlobalApplication.TARGET_IDX, commentIdx)
        intent.putExtra(GlobalApplication.REPORT_TARGET, ReportTarget.ABTEST_COMMENT)
        startActivityHorizontal(intent)
    }

    override fun clickLikeBtn() { /* empty function, only qna activity use this function */ }
}