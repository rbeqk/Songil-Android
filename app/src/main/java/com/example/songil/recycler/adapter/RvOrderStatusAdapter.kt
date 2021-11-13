package com.example.songil.recycler.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.data.Order
import com.example.songil.databinding.ItemOrderStatusBinding
import com.example.songil.page_inquiry.InquiryActivity

class RvOrderStatusAdapter(private val context : Context, private val dataList : ArrayList<Order>) : RecyclerView.Adapter<RvOrderStatusAdapter.ViewHolder>() {

    private lateinit var binding : ItemOrderStatusBinding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    class ViewHolder(binding : ItemOrderStatusBinding) : RecyclerView.ViewHolder(binding.root){
        val btnPaymentInfo = binding.btnPaymentInformation
        val btnDeliveryStatus = binding.btnDeliveryStatus
        val btnInquiry = binding.btnInquiry
        val btnOrderCancel = binding.btnCancelOrder
        val btnComment = binding.btnGoComment
        val orderStatus = binding.tvOrderStatus
        val productName = binding.tvCraftName
        val artistName = binding.tvArtistName
        val price = binding.tvPrice
        val amount = binding.tvCount
        val image = binding.ivPhoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemOrderStatusBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = dataList[position].productName
        holder.artistName.text = dataList[position].artistName
        holder.orderStatus.text = dataList[position].orderStatus
        holder.price.text = context.getString(R.string.form_price_won, dataList[position].price)
        // set buttons
        holder.btnComment.setOnClickListener {
            Log.d("order", "코멘트 작성 to ${dataList[position].productIdx}")
        }
        holder.btnDeliveryStatus.setOnClickListener {
            Log.d("order", "배송 정보 to ${dataList[position].orderStatus}")
        }
        holder.btnInquiry.setOnClickListener {
            //Log.d("order", "문의 작성 to ${dataList[position].productIdx}")
            context.startActivity(Intent(context as Activity, InquiryActivity::class.java))
        }
        holder.btnOrderCancel.setOnClickListener {
            Log.d("order", "주문 취소 to ${dataList[position].orderIdx}")
        }
        holder.btnPaymentInfo.setOnClickListener {
            Log.d("order", "결제정보 확인 to ${dataList[position].orderIdx}")
        }
        holder.amount.text = context.getString(R.string.form_single_count, dataList[position].amount)
        holder.btnComment.text = "코멘트 작성하러 가기"
        Glide.with(context).load(dataList[position].productThumbnail).into(holder.image)
        if (checkDelivery(dataList[position].orderStatus)){
            holder.btnOrderCancel.isEnabled = false
        }
    }

    override fun getItemCount(): Int = dataList.size

    private fun checkDelivery(deliveryStatus : String) : Boolean{
        return (deliveryStatus == "배송완료")
    }

}