package com.ian.fastcam.chap8

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations.map
import com.ian.fastcam.databinding.Chap8FrameBinding

class FrameFragment : Fragment() {
    private lateinit var binding: Chap8FrameBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap8FrameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val images = (it.getStringArray("images")?: emptyArray()).map {
                 uriString ->
                    FrameItem(Uri.parse(uriString))
            }.toList()

            val frameAdapter = FrameAdapter(images)
            binding.viewPager.adapter = frameAdapter
        }
    }
}