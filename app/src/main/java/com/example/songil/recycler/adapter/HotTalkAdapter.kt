package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.data.HotTalk
import com.example.songil.databinding.ItemHottalkAbtestBinding
import com.example.songil.databinding.ItemHottalkQnaBinding

class HotTalkAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val hotTalkData = ArrayList<HotTalk>()
    private val qna = 0 // 그 외는 abTest

    class HotTalkQnaViewHolder(binding : ItemHottalkQnaBinding) : RecyclerView.ViewHolder(binding.root){
        val category = binding.tvCategory
        val title = binding.tvTitle
        val commentCount = binding.tvCommentCount
    }
    class HotTalkAbTestViewHolder(binding : ItemHottalkAbtestBinding) : RecyclerView.ViewHolder(binding.root){
        val category = binding.tvCategory
        val artistImage = binding.ivProfile
        val artistName = binding.tvArtistName
    }

    override fun getItemViewType(position: Int): Int {
        return if (hotTalkData[position].category == "QnA") 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            qna -> {
                val binding = ItemHottalkQnaBinding.inflate(inflater, parent, false)
                HotTalkQnaViewHolder(binding)
            }
            else -> {
                val binding = ItemHottalkAbtestBinding.inflate(inflater, parent, false)
                HotTalkAbTestViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType){
            qna -> {
                (holder as HotTalkQnaViewHolder).category.text = hotTalkData[position].category
                holder.commentCount.text = hotTalkData[position].commentCnt.toString()
                holder.title.text = hotTalkData[position].title
            }
            else -> {
                (holder as HotTalkAbTestViewHolder).category.text = hotTalkData[position].category
                holder.artistName.text = hotTalkData[position].title
                Glide.with(holder.itemView.context).load(hotTalkData[position].artistImageUrl).into(holder.artistImage)
            }
        }
    }

    override fun getItemCount(): Int = hotTalkData.size

    fun applyData(newData : ArrayList<HotTalk>){
        hotTalkData.clear()
        hotTalkData.addAll(newData)
        notifyDataSetChanged()
    }
}
