package com.songil.songil.page_qna

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.*
import com.songil.songil.databinding.ChatActivityBinding
import com.songil.songil.page_qnawrite.QnaWriteActivity
import com.songil.songil.page_report.ReportActivity
import com.songil.songil.popup_block.BlockDialog
import com.songil.songil.popup_block.PopupBlockView
import com.songil.songil.popup_more.MoreBottomSheet
import com.songil.songil.popup_more.popup_interface.PopupMoreView
import com.songil.songil.popup_remove.RemoveDialog
import com.songil.songil.popup_remove.popup_interface.PopupRemoveView
import com.songil.songil.popup_warning.SocketTimeoutDialog
import com.songil.songil.popup_warning.WarningDialog
import com.songil.songil.recycler.adapter.PostAndChatAdapter
import com.songil.songil.recycler.rv_interface.RvPostAndChatView
import com.songil.songil.utils.softKeyboardCallback.KeyboardVisibilityUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class QnaActivity : BaseActivity<ChatActivityBinding>(R.layout.chat_activity), RvPostAndChatView, PopupMoreView, PopupRemoveView,
    PopupBlockView {

    private val viewModel : QnaViewModel by lazy { ViewModelProvider(this)[QnaViewModel::class.java] }
    private lateinit var keyboardVisibilityUtils : KeyboardVisibilityUtils
    private lateinit var writeResult : ActivityResultLauncher<Intent>
    private var itemIsExists = true // 현재 조회중인 아이템이 존재하는지 여부
    private var itemIsLoaded = false // 조회하려는 Qna 요청에 대한 서버의 응답을 받았는지 여부

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
                viewModel.tryGetQna()
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

        viewModel.tryGetQna()
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
        // get QnA 결과 observer
        val qnaResult = Observer<Int>{ liveData ->
            itemIsLoaded = true
            when (liveData){
                200 -> {
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                    itemIsExists = true
                }
                2312 -> {
                    val dialog = WarningDialog("삭제된 QnA입니다.", "이전 페이지로 이동되며\n바로 새로고침됩니다."){
                        itemIsExists = false
                        finish()
                    }
                    dialog.show(supportFragmentManager, dialog.tag)
                }
            }
            binding.btnRegister.isClickable = true
        }
        viewModel.loadQna.observe(this, qnaResult)

        // 댓글 등록 observer
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

        // 댓글 삭제 observer
        val removeCommentResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                }
            }
        }
        viewModel.deleteCommentResult.observe(this, removeCommentResult)

        // 좋아요 변경 결과 observer
        val changeLikeObserver = Observer<Boolean>{ liveData ->
            if (liveData){
                if ((binding.rvComment.adapter as PostAndChatAdapter).itemCount > 0) {
                    (binding.rvComment.adapter as PostAndChatAdapter).notifyItemChanged(0, "like")
                }
            }
        }
        viewModel.changeLikeResult.observe(this, changeLikeObserver)

        // QnA 삭제 observer
        val removeQnaResult = Observer<Boolean> { liveData ->
            if (liveData){
                finish()
            } else {
                showSimpleToastMessage("QnA 삭제에 실패했습니다. 잠시 후에 다시 시도해주세요.")
            }
        }
        viewModel.deleteQnaResult.observe(this, removeQnaResult)

        val networkErrorObserver = Observer<BaseViewModel.FetchState>{ _ ->
            itemIsExists = false
            val dialog = SocketTimeoutDialog(true)
            dialog.show(supportFragmentManager, dialog.tag)
        }
        viewModel.fetchState.observe(this, networkErrorObserver)

        // 댓글을 통한 사용자 차단 결과 observer
        val blockCommentUserObserver = Observer<Boolean> { result ->
            if (result) {
                (binding.rvComment.adapter as PostAndChatAdapter).refresh()
            } else {
                showSimpleToastMessage("사용자 차단에 실패했습니다. 잠시 후에 시도해주세요.")
            }
        }
        viewModel.blockCommentUserResult.observe(this, blockCommentUserObserver)

        // 본문의 더보기 버튼을 통한 작성자 차단 결과 observer
        val blockWriterObserver = Observer<Boolean>{ result ->
            if (result){
                itemIsExists = false
                finish()
            } else {
                showSimpleToastMessage("사용자 차단에 실패했습니다. 잠시 후에 시도해주세요.")
            }
        }
        viewModel.blockWriterResult.observe(this, blockWriterObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            if (GlobalApplication.checkIsLogin()){
                viewModel.tryWriteComment(binding.etComment.text.toString())
                binding.btnRegister.isClickable = false
            } else {
                callNeedLoginDialog()
            }
        }

        binding.btnMore.setOnClickListener {
            if (itemIsLoaded){
                val moreBottomSheet = MoreBottomSheet(this, (viewModel.getIsWriter()))
                moreBottomSheet.show(supportFragmentManager, moreBottomSheet.tag)
            } else {
                showSimpleToastMessage(getString(R.string.data_loading))
            }
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
        if (itemIsExists && itemIsLoaded){
            val intent = Intent(this, BaseActivity::class.java)
            intent.putExtra("QNA", viewModel.getQna)
            setResult(RESULT_OK, intent)
        } else if (!itemIsExists && itemIsLoaded) {
            val intent = Intent(this, BaseActivity::class.java)
            setResult(RESULT_OK, intent)
        }
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

    // 채팅에서 차단하기 버튼을 클릭했을 때 이벤트
    override fun blockChatUser(targetUserIdx: Int) {
        if (isLogin()){
            val dialog = BlockDialog(this, targetUserIdx)
            dialog.show(supportFragmentManager, dialog.tag)
        } else {
            callNeedLoginDialog()
        }
    }

    override fun clickLikeBtn() {
        if (GlobalApplication.checkIsLogin()){
            viewModel.tryToggleLike()
        } else {
            callNeedLoginDialog()
        }
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
        if (isLogin()){
            val intent = Intent(this, ReportActivity::class.java)
            intent.putExtra(GlobalApplication.REPORT_TARGET, ReportTarget.QNA)
            intent.putExtra(GlobalApplication.TARGET_IDX, viewModel.qnaIdx)
            startActivityHorizontal(intent)
        } else {
            callNeedLoginDialog()
        }
    }

    // 더보기 클릭시 아래에서 올라오는 항목 중 차단하기 클릭시 이벤트
    // 차단 확인 dialog 호출
    override fun bottomSheetBlockClick() {
        if (isLogin()){
            val dialog = BlockDialog(this)
            dialog.show(supportFragmentManager, dialog.tag)
        } else {
            callNeedLoginDialog()
        }
    }

    override fun popupRemoveClick() {
        viewModel.tryRemoveQna()
    }

    // 차단하기 클릭시 발생하는 dialog 에서 예 클릭시 이벤트
    // targetIdx 가 null 인 경우는 해당 본문을 통한 차단하기 이벤트가 발생한 경우
    // targetIdx 가 null 이 아닌 경우에는 채팅에서 차단을 클릭한 경우
    override fun block(targetIdx: Int?) {
        if (targetIdx == null){
            viewModel.tryBlockWriter()
        } else {
            viewModel.tryBlockUserByChat(targetIdx)
        }
    }
}