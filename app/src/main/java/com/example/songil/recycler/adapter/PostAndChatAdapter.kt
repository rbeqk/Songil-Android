package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.data.ABTest
import com.example.songil.data.Chat
import com.example.songil.data.Post
import com.example.songil.data.WithQna
import com.example.songil.databinding.ItemAbtestBinding
import com.example.songil.databinding.ItemPostCommentBinding
import com.example.songil.databinding.ItemPostContentQnaBinding
import com.example.songil.recycler.rv_interface.RvPostAndChatView
import com.example.songil.recycler.viewholder.AbTestViewHolder
import com.example.songil.recycler.viewholder.PostCommentViewHolder
import com.example.songil.recycler.viewholder.QnaViewHolder

class PostAndChatAdapter(private val view : RvPostAndChatView) : PagingDataAdapter<Post, RecyclerView.ViewHolder>(diffCallback) {

    // 대댓글 추가할 때 어떤 댓글에 대댓글을 작성할지 표시하기 위해 사용
    private var pointPosition : Int? = null
    private var headerIdx : Int ?= null

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return if (oldItem is Chat && newItem is Chat){
                    (oldItem == newItem)
                } else (oldItem is ABTest && newItem is ABTest) || (oldItem is WithQna && newItem is WithQna)
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return if (oldItem is Chat && newItem is Chat){
                    (oldItem.isDeleted == newItem.isDeleted && oldItem.reComment == newItem.reComment)
                }
                else if (oldItem is WithQna && newItem is WithQna) {
                    (oldItem.totalCommentCnt == newItem.totalCommentCnt && oldItem.totalLikeCnt == newItem.totalLikeCnt
                            && oldItem.content == newItem.content && oldItem.title == newItem.title)
                } else {
                    false
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)){
            is Chat -> { 0 }
            is WithQna -> { 1 }
            is ABTest -> { 2 }
            else -> { -1 }
        }
    }

    // about notifyItemChanged animation
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
        }
        else {
            for (payload in payloads){
                if (payload is String){
                    if (payload == "click_reply" && holder is PostCommentViewHolder){
                        holder.replyBtn.text = if (position == pointPosition){
                            holder.itemView.context.getString(R.string.cancel_write_reply)
                        } else {
                            holder.itemView.context.getString(R.string.comment_reply)
                        }
                    }
                    if (payload == "like" && headerIdx != null){
                        val item = getItem(headerIdx!!)
                        if (holder is QnaViewHolder){
                            item as WithQna
                            holder.favCnt.text = item.totalLikeCnt.toString()
                            holder.icFav.setImageResource(if (item.isLike == "Y") R.drawable.ic_heart_base_16 else R.drawable.ic_heart_line_16)
                        }
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            when (holder) {
                is PostCommentViewHolder -> {
                    item as Chat
                    val isChatWriter = item.isWriter == "Y"
                    if (item.isDeleted == "Y"){ // 삭제된 댓글
                        holder.content.text = holder.itemView.context.getString(R.string.comment_remove_content)
                        holder.userName.text = holder.itemView.context.getString(R.string.comment_remove_user)
                        holder.date.visibility = View.GONE
                        holder.replyBtn.visibility = View.GONE
                        holder.reportOrRemoveBtn.visibility = View.GONE
                        holder.isWriter.visibility = View.GONE
                    } else {
                        holder.date.visibility = View.VISIBLE
                        holder.replyBtn.visibility = View.VISIBLE
                        holder.reportOrRemoveBtn.visibility = View.VISIBLE
                        holder.isWriter.visibility = View.VISIBLE
                        holder.content.text = item.comment
                        holder.userName.text = item.userName
                        holder.date.text = item.createdAt
                        holder.reportOrRemoveBtn.text = if (item.isUserComment == "Y") holder.itemView.context.getString(R.string.remove_with_underline) else holder.itemView.context.getString(R.string.report_with_underline)
                        holder.reportOrRemoveBtn.setOnClickListener {
                            if (item.isUserComment == "Y"){ view.removeChat(item.commentIdx) }
                            else { view.reportChat(item.commentIdx) }
                        }
                        holder.replyBtn.text = if (position == pointPosition){
                            holder.itemView.context.getString(R.string.cancel_write_reply)
                        } else {
                            holder.itemView.context.getString(R.string.comment_reply)
                        }
                        holder.replyBtn.setOnClickListener {
                            view.clickReply(item.commentIdx, item.userName)
                            setPointPosition(position)
                        }
                        holder.isWriter.visibility = if (isChatWriter) View.VISIBLE else View.INVISIBLE
                        Glide.with(holder.itemView.context).load(item.userProfile).into(holder.profile)
                    }
                    holder.replies.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
                    holder.replies.adapter = ChatReplyAdapter(item.reComment, view)
                }
                is QnaViewHolder -> {
                    headerIdx = position
                    item as WithQna
                    holder.content.text = item.content
                    holder.icFav.setImageResource(if (item.isLike == "Y") R.drawable.ic_heart_base_16 else R.drawable.ic_heart_line_16)
                    holder.title.text = item.title
                    Glide.with(holder.itemView.context).load(item.userProfile).into(holder.profile)
                    holder.userName.text = item.userName
                    holder.date.text = item.createdAt
                    holder.commentCnt.text = item.totalCommentCnt.toString()
                    holder.favCnt.text = item.totalLikeCnt.toString()
                    holder.favBtn.setOnClickListener {
                        view.clickLikeBtn()
                    }
                }
                is AbTestViewHolder -> {
                    headerIdx = position
                    item as ABTest
                    var choice = item.voteInfo?.vote
                    holder.artistName.text = item.artistName
                    holder.content.text = item.content
                    holder.date.text = item.deadline
                    holder.commentCount.text = item.totalCommentCnt.toString()
                    if (item.artistImageUrl != null) Glide.with(holder.itemView.context).load(item.artistImageUrl).into(holder.artistImage)
                    Glide.with(holder.itemView.context).load(item.imageA).into(holder.photoA)
                    Glide.with(holder.itemView.context).load(item.imageB).into(holder.photoB)
                    holder.photoA.setOnClickListener {
                        if (item.voteInfo == null) {
                            if (choice == "A") {
                                holder.layoutA.visibility = View.GONE
                                choice = null
                            } else {
                                holder.layoutA.visibility = View.VISIBLE
                                holder.layoutB.visibility = View.GONE
                                choice = "A"
                            }
                        }
                    }
                    holder.photoB.setOnClickListener {
                        if (item.voteInfo == null) {
                            if (choice == "B") {
                                holder.layoutB.visibility = View.GONE
                                choice = null
                            } else {
                                holder.layoutB.visibility = View.VISIBLE
                                holder.layoutA.visibility = View.GONE
                                choice = "B"
                            }
                        }
                    }
                    when {
                        (item.isFinished == "Y") -> { // 투표 종료 기간이 지난 경우
                            applyVoteState(holder, true)
                            applyVote(holder, item.finalInfo!!.vote, item.finalInfo.percent, item.finalInfo.totalVoteCnt)
                        }
                        (item.voteInfo == null) -> { // 투표 가능한 기간 내 투표를 안한 경우
                            holder.layoutA.visibility = View.GONE
                            holder.layoutB.visibility = View.GONE
                            applyVoteState(holder, isFinish = false, isVoted = false)
                            holder.voteBtn.setOnClickListener {
                                if (choice != null) view.vote(item.abTestIdx, choice!!)
                            }
                        }
                        else -> { // 투표 가능한 기간 내 투표를 완료한 경우
                            applyVoteState(holder, isFinish = false, isVoted = true)
                            applyVote(holder, item.voteInfo.vote, item.voteInfo.percent, item.voteInfo.totalVoteCnt)
                            holder.voteBtn.setOnClickListener {
                                 view.cancelVote(item.abTestIdx)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> {
                val binding = ItemPostCommentBinding.inflate(inflater, parent, false)
                PostCommentViewHolder(binding)
            }
            1 -> {
                val binding = ItemPostContentQnaBinding.inflate(inflater, parent, false)
                QnaViewHolder(binding)
            }
            else -> {
                val binding = ItemAbtestBinding.inflate(inflater, parent, false)
                AbTestViewHolder(binding)
            }
        }
    }

    fun setPointPosition(newPointPosition : Int?){
        // newPointPosition 이 null 인 경우, 기존 pointPosition 이 null 일 수는 없다.
        val prevPointPosition = pointPosition
        if (newPointPosition == null || newPointPosition == pointPosition){
            pointPosition = null
            prevPointPosition?.let { notifyItemChanged(it, "click_reply") }
        }
        else {
            pointPosition = newPointPosition
            notifyItemChanged(newPointPosition, "click_reply")
            prevPointPosition?.let { notifyItemChanged(it, "click_reply") }
        }
    }

    fun getPointPosition() : Int? {
        return pointPosition
    }

    // use only ab-test viewHolder
    private fun setTextButtonState(enable : Boolean, view : AppCompatTextView){
        val viewContext = view.context
        if (enable){
            view.setBackgroundColor(ContextCompat.getColor(viewContext, R.color.songil_2))
        } else {
            view.setBackgroundColor(ContextCompat.getColor(viewContext, R.color.g_3))
        }
        view.isClickable = enable
    }

    private fun applyVoteState(holder : AbTestViewHolder, isFinish : Boolean, isVoted : Boolean = false){
        when {
            isFinish -> {   // 투표 종료, 이 경우 투표 여부는 상관하지 않음
                setTextButtonState(false, holder.voteBtn)
                holder.voteBtn.text = holder.itemView.context.getString(R.string.closed_voting)
                holder.checkImageA.visibility = View.GONE
                holder.checkImageB.visibility = View.GONE
                holder.rateA.visibility = View.VISIBLE
                holder.rateB.visibility = View.VISIBLE
                holder.photoA.isClickable = false
                holder.photoB.isClickable = false
            }
            isVoted -> {    // 진행중인 투표, 투표 완료
                setTextButtonState(true, holder.voteBtn)
                holder.voteBtn.text = holder.itemView.context.getString(R.string.do_re_vote)
                holder.checkImageA.visibility = View.VISIBLE
                holder.checkImageB.visibility = View.VISIBLE
                holder.rateA.visibility = View.VISIBLE
                holder.rateB.visibility = View.VISIBLE
            }
            else -> {   // 진행중인 투표, 투표 미완료
                setTextButtonState(true, holder.voteBtn)
                holder.voteBtn.text = holder.itemView.context.getString(R.string.do_vote)
                holder.checkImageA.visibility = View.VISIBLE
                holder.checkImageB.visibility = View.VISIBLE
                holder.rateA.visibility = View.GONE
                holder.rateB.visibility = View.GONE
                holder.photoA.isClickable = true
                holder.photoB.isClickable = true
            }
        }
    }

    private fun applyVote(holder : AbTestViewHolder, select : String, percent : Int, count : Int){
        when (select) {
            "A" -> {
                holder.layoutA.visibility = View.VISIBLE
                holder.layoutB.visibility = View.GONE
                holder.rateA.text = holder.itemView.context.getString(R.string.form_vote_rate, percent, count)
            }
            "B" -> {
                holder.layoutA.visibility = View.GONE
                holder.layoutB.visibility = View.VISIBLE
                holder.rateB.text = holder.itemView.context.getString(R.string.form_vote_rate, percent, count)
            }
            else -> {
                holder.layoutA.visibility = View.GONE
                holder.layoutB.visibility = View.GONE
            }
        }
    }
}