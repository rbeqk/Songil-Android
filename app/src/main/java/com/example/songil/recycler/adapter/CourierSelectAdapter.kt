package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.databinding.ItemCourierCompanyBinding

class CourierSelectAdapter(private val companyList : Map<String, String>, private val selectCompany : (String) -> Unit) : RecyclerView.Adapter<CourierSelectAdapter.CourierCompanyViewHolder>(){
    inner class CourierCompanyViewHolder(binding : ItemCourierCompanyBinding) : RecyclerView.ViewHolder(binding.root){
        val root = binding.layoutMain
        val companyName = binding.tvCompanyName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourierCompanyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCourierCompanyBinding.inflate(inflater, parent, false)
        return CourierCompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourierCompanyViewHolder, position: Int) {
        holder.companyName.text = companyList.values.elementAt(position)
        holder.root.setOnClickListener {
            selectCompany(companyList.keys.elementAt(position))
        }
    }

    override fun getItemCount(): Int = companyList.size
}