package com.ian.fastcam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ian.fastcam.databinding.FragmentChapterSelectorBinding

class ChapterSelect : Fragment() {
    private lateinit var binding : FragmentChapterSelectorBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChapterSelectorBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chap3Btn.setOnClickListener {
            findNavController().navigate(R.id.action_chapterSelect_to_unitConversion)
        }
        binding.chap4Btn.setOnClickListener {
            findNavController().navigate(R.id.action_chapterSelect_to_emergencyMedical)
        }
        binding.chap5Btn.setOnClickListener {
            findNavController().navigate(R.id.action_chapterSelect_to_calculator)
        }
    }
}