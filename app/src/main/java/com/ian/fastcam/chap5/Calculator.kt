package com.ian.fastcam.chap5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ian.fastcam.databinding.Chap5Binding

class Calculator : Fragment() {
    private lateinit var binding : Chap5Binding
    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap5Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}