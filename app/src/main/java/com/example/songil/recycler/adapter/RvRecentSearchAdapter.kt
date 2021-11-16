package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.databinding.ItemSearchRecentsearchBinding
import com.example.songil.recycler.rv_interface.RvTriggerView

class RvRecentSearchAdapter(context : Context, private val triggerView: RvTriggerView) : RecyclerView.Adapter<RvRecentSearchAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemSearchRecentsearchBinding
    private var dataList = ArrayList<String>()

    class ViewHolder(binding : ItemSearchRecentsearchBinding) : RecyclerView.ViewHolder(binding.root){
        val word = binding.tvName
        val removeBtn = binding.btnRemove
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSearchRecentsearchBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.word.text = dataList[position]
        holder.removeBtn.setOnClickListener {
            triggerView.notifyDataChange(0, position)
        }
        holder.word.setOnClickListener {
            triggerView.notifyDataChange(1, position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<String>){
        dataList = newData
        notifyDataSetChanged()
    }
}