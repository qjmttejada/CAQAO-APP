package com.example.caqao.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.caqao.databinding.GalleryViewItemBinding
import com.example.caqao.network.GalleryPhoto

class GalleryGridAdapter : ListAdapter<GalleryPhoto,
        GalleryGridAdapter.GalleryPhotoViewHolder>(DiffCallback) {

    class GalleryPhotoViewHolder(private var binding:
                                 GalleryViewItemBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(GalleryPhoto: GalleryPhoto){
            binding.photo = GalleryPhoto
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<GalleryPhoto>(){
        override fun areItemsTheSame(oldItem: GalleryPhoto, newItem: GalleryPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GalleryPhoto, newItem: GalleryPhoto): Boolean {
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            GalleryPhotoViewHolder{
        return GalleryPhotoViewHolder(GalleryViewItemBinding.inflate(
            LayoutInflater.from(parent.context)
        ))
    }

    override fun onBindViewHolder(holder: GalleryPhotoViewHolder,
                                  position: Int){
        val galleryPhoto = getItem(position)
        holder.bind(galleryPhoto)
    }

}
