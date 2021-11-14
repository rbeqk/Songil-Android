package com.example.songil.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.databinding.ShoppingbasketItemCraftBinding
import com.example.songil.page_basket.BasketViewModel
import com.example.songil.page_basket.models.BasketItem
import com.example.songil.recycler.rv_interface.RvTriggerView

class RvShoppginBasketAdapter(private val context : Context, private val viewModel : BasketViewModel) : RecyclerView.Adapter<RvShoppginBasketAdapter.ViewHolder>() {

    private var dataList = ArrayList<BasketItem>()
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ShoppingbasketItemCraftBinding

    class ViewHolder(binding : ShoppingbasketItemCraftBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivPhoto
        val craftName = binding.tvCraft
        val craftCount = binding.tvCount
        val artistName = binding.tvMaker
        val price = binding.tvPrice
        val checkBox = binding.cbSelect
        val minusBtn = binding.btnMinus
        val plusBtn = binding.btnPlus
        val removeBtn = binding.btnRemove
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ShoppingbasketItemCraftBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].thumbNailImg).into(holder.image)
        holder.craftCount.text = dataList[position].amount.toString()
        holder.craftName.text = dataList[position].productName
        holder.artistName.text = dataList[position].artistName
        holder.checkBox.setOnClickListener{
            viewModel.toggleCheckButton(position)
        }
        holder.minusBtn.setOnClickListener {
            viewModel.changeItemAmount(position, false)
        }
        holder.plusBtn.setOnClickListener {
            viewModel.changeItemAmount(position, true)
        }
        holder.removeBtn.setOnClickListener {
            viewModel.removeItem(position)
            notifyDataSetChanged()
        }
        holder.checkBox.isChecked = dataList[position].checked
        holder.price.text = context.getString(R.string.form_price_won, dataList[position].price)
    }

    override fun getItemCount(): Int = dataList.size

    fun applyData(newData : ArrayList<BasketItem>){
        dataList = newData
        notifyDataSetChanged()
    }

    fun changeData(){
        notifyDataSetChanged()
    }
}