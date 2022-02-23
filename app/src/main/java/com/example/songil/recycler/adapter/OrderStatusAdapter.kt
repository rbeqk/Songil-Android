package com.example.songil.recycler.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.config.InquiryTarget
import com.example.songil.databinding.ItemOrderStatusBinding
import com.example.songil.page_cancel.CancelActivity
import com.example.songil.page_delivery.DeliveryActivity
import com.example.songil.page_inquiry.InquiryActivity
import com.example.songil.page_orderstatus.models.OrderStatusItemInfo
import com.example.songil.page_payinfo.PayInfoActivity
import com.example.songil.utils.changeToPriceForm

class OrderStatusAdapter(private val context : Context, private val dataList : ArrayList<OrderStatusItemInfo>) : RecyclerView.Adapter<OrderStatusAdapter.ViewHolder>() {

    private lateinit var binding : ItemOrderStatusBinding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val orderStatusString = mapOf(1 to R.string.preparing_for_delivery, 2 to R.string.in_transit, 3 to R.string.delivered,
            4 to R.string.request_cancel, 5 to R.string.cancel_request_denied, 6 to R.string.cancelled_complete, 7 to R.string.request_return,
            8 to R.string.return_request_denied, 9 to R.string.return_complete)

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
        holder.productName.text = dataList[position].name
        holder.artistName.text = dataList[position].artistName
        holder.orderStatus.text = context.getString(orderStatusString[dataList[position].status]!!)
        holder.price.text = context.getString(R.string.form_price_won_string, changeToPriceForm(dataList[position].price))
        // set buttons
        holder.btnComment.setOnClickListener {

        }
        holder.btnDeliveryStatus.setOnClickListener {
            //Log.d("order", "배송 정보 to ${dataList[position].orderStatus}")
            val intent = Intent(context, DeliveryActivity::class.java)
            intent.putExtra("ORDER_DETAIL_IDX", dataList[position].orderDetailIdx)
            (context as BaseActivity<*>).startActivityHorizontal(intent)
        }
        holder.btnInquiry.setOnClickListener {
            //Log.d("order", "문의 작성 to ${dataList[position].productIdx}")
            val intent = Intent(context as Activity, InquiryActivity::class.java)
            intent.putExtra(GlobalApplication.TARGET_IDX, dataList[position].orderDetailIdx)
            intent.putExtra(GlobalApplication.TARGET_IDX_TYPE, InquiryTarget.ORDER)
            context.startActivity(intent)
        }
        holder.btnOrderCancel.setOnClickListener {
            //Log.d("order", "주문 취소 to ${dataList[position].orderIdx}")
            context.startActivity(Intent(context as Activity, CancelActivity::class.java))
        }
        holder.btnPaymentInfo.setOnClickListener {
            val intent = Intent(context as BaseActivity<*>, PayInfoActivity::class.java)
            intent.putExtra("ORDER_DETAIL_IDX", dataList[position].orderDetailIdx)
            context.startActivityHorizontal(intent)
        }
        holder.amount.text = context.getString(R.string.form_single_count, dataList[position].amount)
        holder.btnComment.text = context.getString(R.string.go_write_comment)
        Glide.with(context).load(dataList[position].mainImageUrl).into(holder.image)
        // canReqCancel 이 null 이 아닌 경우는 아직 주문이 취소 가능한 경우
        // canReqCancel 이 null 인 경우는 canReqReturn 이 null 이 아니며, 이는 반품이 가능한 경우
        holder.btnComment.isEnabled = (dataList[position].canWriteComment == "Y")
        if (dataList[position].canReqCancel != null){
            holder.btnOrderCancel.text = context.getString(R.string.request_cancel)
            holder.btnOrderCancel.isEnabled = (dataList[position].canReqCancel == "Y")
        }
        else if (dataList[position].canReqReturn != null) {
            holder.btnOrderCancel.text = context.getString(R.string.request_return)
            holder.btnOrderCancel.isEnabled = (dataList[position].canReqReturn == "Y")
        }

    }

    override fun getItemCount(): Int = dataList.size


}