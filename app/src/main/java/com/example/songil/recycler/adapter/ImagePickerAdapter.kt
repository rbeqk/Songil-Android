package com.example.songil.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.songil.databinding.ItemImagePickerBinding
import com.example.songil.page_imagepicker.ImagePickerData
import com.example.songil.recycler.rv_interface.RvImagePickerView

class ImagePickerAdapter(private val view : RvImagePickerView, private val min : Int = 0, private val max : Int = 3) : RecyclerView.Adapter<ImagePickerAdapter.ImagePickerViewHolder>() {

    private val imageList = ArrayList<ImagePickerData>()
    private val selectPosition = ArrayList<Int>()

    class ImagePickerViewHolder(binding : ItemImagePickerBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.ivImage
        val isChecked = binding.ivCheck
        val layoutMain = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImagePickerBinding.inflate(inflater, parent, false)
        return ImagePickerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagePickerViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(imageList[position].imageUri).into(holder.image)
        holder.isChecked.visibility = if (imageList[position].isChecked) View.VISIBLE else View.INVISIBLE
        holder.layoutMain.setOnClickListener {
            if (imageList[position].isChecked){
                imageList[position].isChecked = false
                notifyItemChanged(position)
                selectPosition.remove(position)
                view.countCheck(selectPosition.size)
            } else if (selectPosition.size in 0 until max){
                imageList[position].isChecked = true
                selectPosition.add(position)
                notifyItemChanged(position)
                view.countCheck(selectPosition.size)
            }
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun applyData(newImageList : ArrayList<ImagePickerData>){
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()
    }

    fun getSelectImageList() : ArrayList<String>{
        val selectImages = ArrayList<String>()
        for (position in selectPosition){
            selectImages.add(imageList[position].imageUri)
        }
        return selectImages
    }
}