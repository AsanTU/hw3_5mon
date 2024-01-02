package com.example.hw3_5mon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.hw3_5mon.databinding.ItemImageBinding

class ImageAdapter(private val list: ArrayList<ImageModel>) :
    Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val binding: ItemImageBinding) : ViewHolder(binding.root) {
        fun onBind(image: ImageModel) {
            binding.pixaImg.load(image.largeImageURL)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewImages(list: ArrayList<ImageModel>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(list[position])
    }
}