package com.example.songil.viewPager2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.R
import com.example.songil.data.TalkWith
import com.example.songil.databinding.ItemMainTalkWithBinding
import kotlin.math.ceil

class Vp2MainTalkWithAdapter(private val context : Context) : RecyclerView.Adapter<Vp2MainTalkWithAdapter.MainTalkWithViewHolder>(){

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemMainTalkWithBinding
    private val dataList = ArrayList<TalkWith>()

    class MainTalkWithViewHolder(binding : ItemMainTalkWithBinding) : RecyclerView.ViewHolder(binding.root){
        val topRoot = binding.layoutTop
        val bottomRoot = binding.layoutBottom
        val topCategory = binding.topCategory
        val bottomCategory = binding.bottomCategory
        val topTitle = binding.topTitle
        val bottomTitle = binding.bottomTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTalkWithViewHolder {
        binding = ItemMainTalkWithBinding.inflate(inflater, parent, false)
        return MainTalkWithViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainTalkWithViewHolder, position: Int) {
        val topPosition = position * 2
        val bottomPosition = position * 2 + 1
        holder.topTitle.text = dataList[topPosition].title
        holder.topCategory.text = dataList[topPosition].category
        holder.topRoot.setOnClickListener {

        }

        if (bottomPosition < dataList.size) {
            holder.bottomTitle.text = dataList[bottomPosition].title
            holder.bottomCategory.text = dataList[bottomPosition].category
            holder.bottomRoot.setOnClickListener {

            }
        } else {
            holder.bottomCategory.visibility = View.GONE
            holder.bottomTitle.text = context.getString(R.string.talk_with_plus_eng)
        }
    }

    override fun getItemCount(): Int = ceil((dataList.size.toDouble() / 2)).toInt()

    fun applyData(newData : ArrayList<TalkWith>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}