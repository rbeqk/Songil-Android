package com.example.songil.recycler.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.config.GlobalApplication
import com.example.songil.data.ClickData
import com.example.songil.databinding.ItemImageBinding
import com.example.songil.page_craft.CraftActivity
import com.example.songil.page_story.StoryActivity

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
                context.startActivity(intent)
            } else {
                val intent = Intent(context, StoryActivity::class.java)
                intent.putExtra(GlobalApplication.STORY_IDX, dataList[position].idx)
                context.startActivity(intent)
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