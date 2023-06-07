package com.ian.fastcam.chap7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ian.fastcam.databinding.Chap7ItemWordBinding

class WordAdapter(private val list : MutableList<Word>, private val itemClickListener: ItemClickListener? = null) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = Chap7ItemWordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = list[position]
        holder.bind(word)
        holder.itemView.setOnClickListener { itemClickListener?.onClick(word) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class WordViewHolder(private val binding: Chap7ItemWordBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(word:Word){
            binding.apply {
                textTextView.text = word.text
                meanTextView.text = word.mean
                typeChip.text = word.type
            }
        }
    }
    interface ItemClickListener{
        fun onClick(word:Word)
    }
}