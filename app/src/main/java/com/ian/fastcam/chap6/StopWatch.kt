package com.ian.fastcam.chap6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ian.fastcam.databinding.Chap6Binding

class StopWatch : Fragment() {
    private lateinit var binding: Chap6Binding
    private var countingStarted = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap6Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startBtn.setOnClickListener {
            start()
            countingStarted = true
            updateBtnUi(countingStarted)
        }
        binding.stopBtn.setOnClickListener {

        }
        binding.pauseBtn.setOnClickListener {
            countingStarted = false
            updateBtnUi(countingStarted)
        }
        binding.lapBtn.setOnClickListener {

        }
    }


    private fun updateBtnUi(bool: Boolean) {
        binding.startBtn.isVisible = !bool
        binding.stopBtn.isVisible = !bool
        binding.pauseBtn.isVisible = bool
        binding.lapBtn.isVisible = bool
    }

    private fun start() {

    }

    private fun stop() {

    }

    private fun pause() {

    }

    private fun lap() {

    }
}