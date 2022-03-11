package com.songil.songil.recycler.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.data.ClickData
import com.songil.songil.databinding.ItemImageBinding
import com.songil.songil.page_craft.CraftActivity
import com.songil.songil.page_story.StoryActivity

class ClickImageAdapter(private val context : Context, private val isCraft : Boolean = true) : RecyclerView.Adapter<ClickImageAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemImageBinding
    private val dataList = ArrayList<ClickData>()

    class ViewHolder(binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivImage
        val main = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemImageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].imageUrl).into(holder.image)
        holder.main.setOnClickListener {
            if (isCraft) {
                val intent = Intent(context, CraftActivity::class.java)
                intent.putExtra(GlobalApplication.CRAFT_IDX, dataList[position].idx)
                (context as BaseActivity<*>).startActivityHorizontal(intent)
            } else {
                val intent = Intent(context, StoryActivity::class.java)
                intent.putExtra(GlobalApplication.STORY_IDX, dataList[position].idx)
                (context as BaseActivity<*>).startActivityHorizontal(intent)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<ClickData>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}