package com.songil.songil.viewPager2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.data.TalkWith
import com.songil.songil.databinding.ItemMainTalkWithBinding
import com.songil.songil.page_abtest.AbtestActivity
import com.songil.songil.page_qna.QnaActivity
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
        holder.topTitle.text = dataList[topPosition].text
        holder.topCategory.text = if (dataList[topPosition].categoryIdx == 1) "QnA" else "AB TEST"
        holder.topRoot.setOnClickListener {
            if (dataList[topPosition].categoryIdx == 1){
                val intent = Intent(context, QnaActivity::class.java)
                intent.putExtra(GlobalApplication.QNA_IDX, dataList[topPosition].idx)
                (context as BaseActivity<*>).startActivityHorizontal(intent)
            } else {
                val intent = Intent(context, AbtestActivity::class.java)
                intent.putExtra(GlobalApplication.ABTEST_IDX, dataList[topPosition].idx)
                (context as BaseActivity<*>).startActivityHorizontal(intent)
            }
        }

        if (bottomPosition < dataList.size) {
            holder.bottomTitle.text = dataList[bottomPosition].text
            holder.bottomCategory.text = if (dataList[topPosition].categoryIdx == 1) "QnA" else "AB TEST"
            holder.bottomRoot.setOnClickListener {
                if (dataList[bottomPosition].categoryIdx == 1){
                    val intent = Intent(context, QnaActivity::class.java)
                    intent.putExtra(GlobalApplication.QNA_IDX, dataList[bottomPosition].idx)
                    (context as BaseActivity<*>).startActivityHorizontal(intent)
                } else {
                    val intent = Intent(context, AbtestActivity::class.java)
                    intent.putExtra(GlobalApplication.ABTEST_IDX, dataList[bottomPosition].idx)
                    (context as BaseActivity<*>).startActivityHorizontal(intent)
                }
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