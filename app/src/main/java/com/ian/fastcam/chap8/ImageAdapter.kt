package com.ian.fastcam.chap8

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ian.fastcam.databinding.Chap8ItemImageBinding
import com.ian.fastcam.databinding.Chap8ItemLoadMoreBinding

sealed class ImageItems {
    data class Image(
        val uri: Uri
    ) : ImageItems()

    object LoadMore : ImageItems()
}

class ImageAdapter(private val itemClickListener: ItemClickListener?=null) : ListAdapter<ImageItems, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<ImageItems>() {
        override fun areItemsTheSame(oldItem: ImageItems, newItem: ImageItems): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ImageItems, newItem: ImageItems): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun getItemCount(): Int {
        val originSize = currentList.size
        return if (originSize == 0) 0 else originSize.inc()
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount.dec() == position) ITEM_LOAD_MODE else ITEM_IMAGE

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return when (viewType) {
            ITEM_IMAGE -> {
                val binding = Chap8ItemImageBinding.inflate(inflater, parent, false)
                ImageViewHolder(binding)
            }
            else -> {
                val binding = Chap8ItemLoadMoreBinding.inflate(inflater, parent, false)
                ItemMoreViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> {
                    holder.bind(currentList[position] as ImageItems.Image)
            }
            is ItemMoreViewHolder -> {
                itemClickListener?.let {
                    holder.bind(itemClickListener)
                }
            }
        }
    }

    companion object {
        const val ITEM_IMAGE = 0
        const val ITEM_LOAD_MODE = 1
    }
}

class ImageViewHolder(private val binding: Chap8ItemImageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ImageItems.Image) {
        binding.previewImageView.setImageURI(item.uri)
    }

}

class ItemMoreViewHolder(private val binding: Chap8ItemLoadMoreBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(itemClickListener: ItemClickListener) {
        itemView.setOnClickListener { itemClickListener.onLoadMoreClick() }

    }

}

interface ItemClickListener {
    fun onLoadMoreClick()
}