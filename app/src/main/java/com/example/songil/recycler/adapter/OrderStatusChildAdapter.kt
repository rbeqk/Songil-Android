package com.example.songil.recycler.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.config.InquiryTarget
import com.example.songil.databinding.ItemOrderStatusBinding
import com.example.songil.page_delivery.DeliveryActivity
import com.example.songil.page_inquiry.InquiryActivity
import com.example.songil.page_orderstatus.models.OrderStatusItemInfo
import com.example.songil.page_payinfo.PayInfoActivity
import com.example.songil.recycler.rv_interface.RvUserOrderStatusView
import com.example.songil.utils.changeToPriceForm

class OrderStatusChildAdapter(private val context : Context, private val dataList : ArrayList<OrderStatusItemInfo>, private val rvView : RvUserOrderStatusView, private val parentPosition : Int) : RecyclerView.Adapter<OrderStatusChildAdapter.ViewHolder>() {

    private lateinit var binding : ItemOrderStatusBinding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val orderStatusString = mapOf(1 to R.string.preparing_for_delivery, 2 to R.string.in_transit, 3 to R.string.delivered,
            4 to R.string.request_cancel, 5 to R.string.cancelled_complete, 6 to R.string.request_return, 7 to R.string.return_complete)
    private val orderStatusColor = mapOf(1 to R.color.green, 2 to R.color.green, 3 to R.color.azul,
            4 to R.color.tomato, 5 to R.color.tomato, 6 to R.color.tomato, 7 to R.color.tomato)

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
        holder.orderStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, orderStatusColor[dataList[position].status] ?: R.color.songil_2))
        holder.price.text = context.getString(R.string.form_price_won_string, changeToPriceForm(dataList[position].price))

        holder.btnComment.setOnClickListener {
            rvView.goToCommentWrite(parentPosition = parentPosition, childPosition = position, orderDetailIdx = dataList[position].orderDetailIdx)
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
            (context as BaseActivity<*>).startActivityHorizontal(intent)
        }

        holder.btnPaymentInfo.setOnClickListener {
            val intent = Intent(context as BaseActivity<*>, PayInfoActivity::class.java)
            intent.putExtra("ORDER_DETAIL_IDX", dataList[position].orderDetailIdx)
            context.startActivityHorizontal(intent)
        }
        holder.amount.text = context.getString(R.string.form_single_count, dataList[position].amount)
        Glide.with(context).load(dataList[position].mainImageUrl).into(holder.image)
        when (dataList[position].commentBtnStatus){
            1 -> {
                holder.btnComment.isEnabled = false
                holder.btnComment.text = context.getString(R.string.go_write_comment)
            }
            2 -> {
                holder.btnComment.isEnabled = true
                holder.btnComment.text = context.getString(R.string.go_write_comment)
            }
            3 -> {
                holder.btnComment.isEnabled = false
                holder.btnComment.text = context.getString(R.string.complete_write_comment)
            }
        }
        // status 가 1, 2, 4, 5 (순서대로 배송준비중, 배송중, 취소요청, 취소완료)인 경우에는 취소 버튼
        // 아닌 경우에는 반품 버튼
        if (dataList[position].status == 1 || dataList[position].status == 2 || dataList[position].status == 4 || dataList[position].status == 5){
            holder.btnOrderCancel.text = context.getString(R.string.request_cancel)
            holder.btnOrderCancel.isEnabled = (dataList[position].canReqCancel == "Y")
            holder.btnOrderCancel.setOnClickListener {
                rvView.requestCancel(parentPosition = parentPosition, childPosition = position, orderDetailIdx = dataList[position].orderDetailIdx)
            }
        }
        else {
            holder.btnOrderCancel.text = context.getString(R.string.request_return)
            holder.btnOrderCancel.isEnabled = (dataList[position].canReqReturn == "Y")
            holder.btnOrderCancel.setOnClickListener {
                rvView.requestReturn(parentPosition = parentPosition, childPosition = position, orderDetailIdx = dataList[position].orderDetailIdx)
            }
        }

    }

    override fun getItemCount(): Int = dataList.size


}