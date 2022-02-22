package com.example.songil.recycler.adapter

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songil.databinding.ItemTermBinding
import com.example.songil.utils.renderAndClose

class PdfTermAdapter(private var renderer : PdfRenderer?, private val pageWidth : Int) : RecyclerView.Adapter<PdfTermAdapter.PdfViewHolder>() {
    inner class PdfViewHolder(private val binding : ItemTermBinding) : RecyclerView.ViewHolder(binding.root){
        fun showPdf(bitmap: Bitmap) = binding.ivItem.setImageBitmap(bitmap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PdfViewHolder(ItemTermBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        renderer?.let {
            holder.showPdf(it.openPage(position).renderAndClose(pageWidth))
        }
    }

    override fun getItemCount(): Int = renderer?.pageCount ?: 0

    fun changeRenderer(newRenderer: PdfRenderer){
        renderer?.close()
        renderer = newRenderer
        notifyDataSetChanged()
    }
}