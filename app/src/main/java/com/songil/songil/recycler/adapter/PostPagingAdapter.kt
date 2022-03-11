package com.songil.songil.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.data.Post
import com.songil.songil.databinding.ItemPostBinding
import com.songil.songil.page_abtest.AbtestActivity
import com.songil.songil.page_qna.QnaActivity
import com.songil.songil.page_story.StoryActivity

class PostPagingAdapter : PagingDataAdapter<Post, PostPagingAdapter.PostViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Post>(){
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return (oldItem.categoryIdx == newItem.categoryIdx) && (oldItem.idx == newItem.idx)
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return (oldItem.isLike == newItem.isLike) && (oldItem.totalLikeCnt == newItem.totalLikeCnt) && (oldItem.title == newItem.title) && (oldItem.content == newItem.content)
            }
        }
    }

    class PostViewHolder(binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.tvTitle
        val commentCount = binding.tvChatCount
        val likeCount = binding.tvFavoriteCount
        val image = binding.ivPhoto
        val content = binding.tvContent
        val userName = binding.tvUser
        val date = binding.tvDate
        val isLike = binding.ivFavorite
        val root = binding.root
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        if (post != null) {
            if (post.mainImageUrl != null){
                holder.image.visibility = View.VISIBLE
                Glide.with(holder.itemView.context).load(post.mainImageUrl).into(holder.image)
            } else { holder.image.visibility = View.GONE }

            if (post.categoryIdx == 3){ // AB-Test
                holder.title.text = holder.itemView.context.getString(R.string.AB_TEST_title, post.name)
                holder.isLike.visibility = View.GONE
                holder.likeCount.visibility = View.GONE
            } else {
                post.title.let { holder.title.text = it }
                holder.isLike.visibility = View.VISIBLE
                holder.likeCount.visibility = View.VISIBLE
                post.isLike.let { isLike ->
                    if (isLike == "Y"){ holder.isLike.setBackgroundResource(R.drawable.ic_heart_base_16) }
                    else { holder.isLike.setBackgroundResource(R.drawable.ic_heart_line_16) }
                }
                post.totalLikeCnt.let { holder.likeCount.text = it.toString() }
            }

            holder.content.text = post.content
            holder.userName.text = post.name
            holder.date.text = post.createdAt
            holder.commentCount.text = post.totalCommentCnt.toString()

            holder.root.setOnClickListener {
                when (post.categoryIdx){
                    1 -> { // story
                        val intent = Intent(holder.itemView.context, StoryActivity::class.java)
                        intent.putExtra(GlobalApplication.STORY_IDX, post.idx)
                        (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
                    }
                    2 -> { // qna
                        val intent = Intent(holder.itemView.context, QnaActivity::class.java)
                        intent.putExtra(GlobalApplication.QNA_IDX, post.idx)
                        (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
                    }
                    3 -> { // ab-test
                        val intent = Intent(holder.itemView.context, AbtestActivity::class.java)
                        intent.putExtra(GlobalApplication.ABTEST_IDX, post.idx)
                        (holder.itemView.context as BaseActivity<*>).startActivityHorizontal(intent)
                    }
                    else -> {
                        throw UnknownError()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }
}