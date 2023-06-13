package com.ian.fastcam.chap8

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ian.fastcam.databinding.Chap8ItemFrameBinding

class FrameAdapter (private val list:List<FrameItem>) : RecyclerView.Adapter<FrameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameViewHolder {
        val binding = Chap8ItemFrameBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FrameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FrameViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size

}
data class FrameItem(
    val uri :Uri
)
class FrameViewHolder (private val binding : Chap8ItemFrameBinding) : RecyclerView.ViewHolder(binding.root){
fun bind(frameItem: FrameItem){
binding.frameImageView.setImageURI(frameItem.uri)
}
}