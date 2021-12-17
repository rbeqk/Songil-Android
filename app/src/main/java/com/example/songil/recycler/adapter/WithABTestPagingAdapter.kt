package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.data.ABTestViewInfo
import com.example.songil.databinding.ItemAbtestBinding

class WithABTestPagingAdapter(diffCallback : DiffUtil.ItemCallback<ABTestViewInfo>) : PagingDataAdapter<ABTestViewInfo, WithABTestPagingAdapter.ABTestViewHolder>(diffCallback){
    class ABTestViewHolder(binding : ItemAbtestBinding) : RecyclerView.ViewHolder(binding.root){
        val artistImage = binding.ivProfile
        val artistName = binding.tvName
        val date = binding.tvDeadline
        val content = binding.tvContent
        val commentCount = binding.tvCommentCount
        val photoA = binding.ivPhotoA
        val photoB = binding.ivPhotoB
        val voteBtn = binding.btnVote
        val layoutA = binding.selectPhotoA
        val rateA = binding.tvRateA
        val checkImageA = binding.ivCheckA
        val layoutB = binding.selectPhotoB
        val rateB = binding.tvRateB
        val checkImageB = binding.ivCheckB
    }

    override fun onBindViewHolder(holder: ABTestViewHolder, position: Int) {
        val abTest = getItem(position)?.abTest
        var choice = getItem(position)?.choice  // choice 는 투표하기버튼을 누르기 전 상태 (예, A 를 클릭한 상태)를 기록하기 위해 사용

        if (abTest != null) {
            val select = abTest.voteInfo?.voteImage // select == null 이면 투표를 아직 안한 상태

            holder.artistName.text = abTest.artistName
            holder.content.text = abTest.content
            holder.date.text = abTest.deadline
            holder.commentCount.text = abTest.totalCommentCnt.toString()
            if (abTest.artistImageUrl != null) Glide.with(holder.itemView.context).load(abTest.artistImageUrl).into(holder.artistImage)
            Glide.with(holder.itemView.context).load(abTest.imageA).into(holder.photoA)
            Glide.with(holder.itemView.context).load(abTest.imageB).into(holder.photoB)

            holder.photoA.setOnClickListener {
                if (select == null) {
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
                if (select == null) {
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

            // 투표 상태 (종료, 투표완료, 투표 안함)에 따라 차이점 구현
            when {
                (abTest.isFinished == "Y") -> { // 투표 종료 기간이 지난 경우
                    applyVoteState(holder, true)
                    applyVote(holder, abTest.finishInfo!!.voteImage, abTest.finishInfo.percent, abTest.finishInfo.totalVoteCnt)
                }
                (select == null) -> { // 투표 가능한 기간 내 투표를 안한 경우
                    applyVoteState(holder, isFinish = false, isVoted = false)
                }
                else -> { // 투표 가능한 기간 내 투표를 완료한 경우
                    applyVoteState(holder, isFinish = false, isVoted = true)
                    applyVote(holder, abTest.voteInfo.voteImage, abTest.voteInfo.percent, abTest.voteInfo.totalVoteCnt)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ABTestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAbtestBinding.inflate(inflater, parent, false)
        return ABTestViewHolder(binding)
    }

    private fun setTextButtonState(enable : Boolean, view : AppCompatTextView){
        val viewContext = view.context
        if (enable){
            view.setBackgroundColor(ContextCompat.getColor(viewContext, R.color.songil_2))
        } else {
            view.setBackgroundColor(ContextCompat.getColor(viewContext, R.color.g_3))
        }
        view.isClickable = enable
    }

    private fun applyVoteState(holder : ABTestViewHolder, isFinish : Boolean, isVoted : Boolean = false){
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

    private fun applyVote(holder : ABTestViewHolder, select : String, percent : Int, count : Int){
        if (select == "A"){
            holder.layoutA.visibility = View.VISIBLE
            holder.layoutB.visibility = View.GONE
            holder.rateA.text = holder.itemView.context.getString(R.string.form_vote_rate, percent, count)
        } else {
            holder.layoutA.visibility = View.GONE
            holder.layoutB.visibility = View.VISIBLE
            holder.rateB.text = holder.itemView.context.getString(R.string.form_vote_rate, percent, count)
        }
    }
}