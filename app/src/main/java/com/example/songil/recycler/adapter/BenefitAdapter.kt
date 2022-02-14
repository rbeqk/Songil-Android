package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.data.Benefit
import com.example.songil.databinding.ItemCouponBinding

class BenefitAdapter(private val context : Context) : RecyclerView.Adapter<BenefitAdapter.ViewHolder>() {

    private val dataList = ArrayList<Benefit>()
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemCouponBinding

    class ViewHolder(binding : ItemCouponBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivPhoto
        val benefitName = binding.tvBenefitName
        val benefitDate = binding.tvBenefitDate
        val benefitInfo = binding.tvBenefitInformation
        val discountInfo = binding.tvBenefitDiscountInfo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCouponBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.benefitName.text = dataList[position].title
        holder.benefitDate.text = context.getString(R.string.benefit_date, dataList[position].deadline)
        holder.benefitInfo.text = dataList[position].detailInfo
        Glide.with(context).load(dataList[position].imageUrl).into(holder.image)
        holder.discountInfo.text = dataList[position].discountInfo
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<Benefit>){
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}