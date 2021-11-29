package com.example.songil.recycler.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.databinding.ItemAddPhotoBinding
import com.example.songil.recycler.rv_interface.RvPhotoView

class RvAddPhotoAdapter(context : Context, private val view : RvPhotoView) :RecyclerView.Adapter<RvAddPhotoAdapter.ViewHolder>() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var binding: ItemAddPhotoBinding
    private val imageList = ArrayList<Uri>()

    class ViewHolder(binding : ItemAddPhotoBinding) : RecyclerView.ViewHolder(binding.root){
        val addBtn = binding.ivAdd
        val image = binding.ivPhoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemAddPhotoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.addBtn.setOnClickListener {
            view.photoItemClick()
        }
        when {
            imageList.size == position -> {
                holder.addBtn.visibility = View.VISIBLE
            }
            imageList.size > position -> {
                holder.addBtn.visibility = View.GONE
                holder.image.setImageURI(imageList[position])
            }
            else -> {
                holder.addBtn.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = 3

    /*fun applyData(newImageList : ArrayList<Uri>){
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()
    }*/

    fun addPhoto(newImage : Uri){
        imageList.add(newImage)
        notifyDataSetChanged()
    }
}