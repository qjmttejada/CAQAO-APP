package com.example.caqao

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.caqao.databinding.GridViewItemBinding
import com.example.caqao.network.CacaoDetection

class CacaoGridAdapter : ListAdapter<CacaoDetection,
        CacaoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CacaoGridAdapter.MarsPhotoViewHolder {
        return MarsPhotoViewHolder(
            GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CacaoGridAdapter.MarsPhotoViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }

    class MarsPhotoViewHolder(private var binding:
                              GridViewItemBinding
    ):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cacaoDetection: CacaoDetection) {
            binding.cacaoDetection = cacaoDetection
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<CacaoDetection>() {

        override fun areItemsTheSame(oldItem: CacaoDetection, newItem: CacaoDetection): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CacaoDetection, newItem: CacaoDetection): Boolean {
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }

    }

}