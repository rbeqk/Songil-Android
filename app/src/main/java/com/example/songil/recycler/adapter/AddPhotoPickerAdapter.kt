package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.databinding.ItemAddPhotoBinding
import com.example.songil.recycler.rv_interface.RvPhotoView
import kotlin.math.min

class AddPhotoPickerAdapter(private val view : RvPhotoView, private val max : Int = 3) : RecyclerView.Adapter<AddPhotoPickerAdapter.AddPhotoPickerViewHolder>(){

    private val imageList = ArrayList<String>()

    class AddPhotoPickerViewHolder(binding :ItemAddPhotoBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivPhoto
        val addBtn = binding.ivAdd
        val removeBtn = binding.btnRemove
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPhotoPickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddPhotoBinding.inflate(inflater, parent, false)
        return AddPhotoPickerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddPhotoPickerViewHolder, position: Int) {
        holder.addBtn.setOnClickListener {
            view.photoItemClick()
        }
        when {
            position < imageList.size -> {
                holder.root.visibility = View.VISIBLE
                holder.addBtn.visibility = View.GONE
                Glide.with(holder.itemView.context).load(imageList[position]).into(holder.image)
                holder.removeBtn.visibility = View.VISIBLE
                holder.removeBtn.setOnClickListener {
                    imageList.removeAt(position)
                    notifyDataSetChanged()
                }
            }
            position == imageList.size -> {
                holder.root.visibility = View.VISIBLE
                holder.addBtn.visibility = View.VISIBLE
                holder.image.setImageResource(0)
                holder.removeBtn.visibility = View.INVISIBLE
            }
            else -> {
                holder.root.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = min(max, imageList.size + 1)

    fun applyData(newImageList : ArrayList<String>){
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()
    }
}