package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.databinding.ItemSearchRecentsearchBinding

class RecentSearchAdapter(private val search : (String) -> Unit, private val delete : (String) -> Unit) : RecyclerView.Adapter<RecentSearchAdapter.ViewHolder>() {

    private lateinit var binding : ItemSearchRecentsearchBinding
    private var dataList = ArrayList<String>()

    class ViewHolder(binding : ItemSearchRecentsearchBinding) : RecyclerView.ViewHolder(binding.root){
        val word = binding.tvName
        val removeBtn = binding.btnRemove
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemSearchRecentsearchBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.word.text = dataList[position]
        holder.removeBtn.setOnClickListener {
            delete(dataList[position])
        }
        holder.word.setOnClickListener {
            search(dataList[position])
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<String>){
        dataList = newData
        notifyDataSetChanged()
    }
}