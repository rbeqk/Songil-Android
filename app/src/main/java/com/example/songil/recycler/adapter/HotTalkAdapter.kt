package com.example.songil.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.data.HotTalk
import com.example.songil.databinding.ItemHottalkAbtestBinding
import com.example.songil.databinding.ItemHottalkQnaBinding
import com.example.songil.page_abtest.AbtestActivity
import com.example.songil.page_qna.QnaActivity

class HotTalkAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val hotTalkData = ArrayList<HotTalk>()
    private val category = mapOf(1 to "Qna", 2 to "AB TEST")

    class HotTalkQnaViewHolder(binding : ItemHottalkQnaBinding) : RecyclerView.ViewHolder(binding.root){
        val category = binding.tvCategory
        val title = binding.tvTitle
        val commentCount = binding.tvCommentCount
        val root = binding.root
    }
    class HotTalkAbTestViewHolder(binding : ItemHottalkAbtestBinding) : RecyclerView.ViewHolder(binding.root){
        val category = binding.tvCategory
        val artistImage = binding.ivProfile
        val artistName = binding.tvArtistName
        val root = binding.root
    }

    override fun getItemViewType(position: Int): Int {
        return hotTalkData[position].categoryIdx
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            1 -> {
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
            1 -> {
                (holder as HotTalkQnaViewHolder).category.text = category[hotTalkData[position].categoryIdx]
                holder.commentCount.text = hotTalkData[position].totalCommentCnt.toString()
                holder.title.text = hotTalkData[position].text
                holder.root.setOnClickListener {
                    val intent = Intent(holder.itemView.context, QnaActivity::class.java)
                    intent.putExtra(GlobalApplication.QNA_IDX, hotTalkData[position].idx)
                    (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
                }
            }
            else -> {
                (holder as HotTalkAbTestViewHolder).category.text = category[hotTalkData[position].categoryIdx]
                holder.artistName.text = hotTalkData[position].text
                Glide.with(holder.itemView.context).load(hotTalkData[position].imageUrl).into(holder.artistImage)
                holder.root.setOnClickListener {
                    val intent = Intent(holder.itemView.context, AbtestActivity::class.java)
                    intent.putExtra(GlobalApplication.ABTEST_IDX, hotTalkData[position].idx)
                    (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
                }
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
