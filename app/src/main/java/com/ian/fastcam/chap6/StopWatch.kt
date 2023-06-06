package com.ian.fastcam.chap6

import android.app.AlertDialog
import android.app.Dialog
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.ian.fastcam.TAG
import com.ian.fastcam.databinding.Chap6Binding
import com.ian.fastcam.databinding.Chap6DialogCounterBinding
import java.util.*
import kotlin.concurrent.timer

class StopWatch : Fragment() {
    private lateinit var binding: Chap6Binding
    private var countingStarted = false
    private var countdownSecond = 10
    private var currentCountdownDeciSecond = countdownSecond * 10
    private var currentDeciSecond = 0
    private var timer: Timer? = null
    private val mHandler = Handler(Looper.getMainLooper())

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
            showAlertDialog()
        }
        binding.pauseBtn.setOnClickListener {
            pause()
            countingStarted = false
            updateBtnUi(countingStarted)
        }
        binding.lapBtn.setOnClickListener {
            lap()

        }

        binding.countdownTextView.setOnClickListener {
            showCountdownSettingDialog()
        }


        initCountdownView()

    }

    private fun initCountdownView() {
        binding.countdownTextView.text = String.format("%02d", countdownSecond)
        binding.countdownProgressBar.progress = 100

    }

    private fun showCountdownSettingDialog() {
        AlertDialog.Builder(requireContext()).apply {
            val dialogBinding = Chap6DialogCounterBinding.inflate(layoutInflater)
            with(dialogBinding.countdownNumberPicker) {
                maxValue = 20
                minValue = 0
                value = countdownSecond //default 10초
            }
            setTitle("카운트다운 설정")
            setView(dialogBinding.root)
            setPositiveButton("확인") { _, _ ->
                countdownSecond = dialogBinding.countdownNumberPicker.value
                currentCountdownDeciSecond = countdownSecond * 10
                binding.countdownTextView.text = String.format("%02d", countdownSecond)
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun updateBtnUi(bool: Boolean) {
        binding.startBtn.isVisible = !bool
        binding.stopBtn.isVisible = !bool
        binding.pauseBtn.isVisible = bool
        binding.lapBtn.isVisible = bool
    }

    private fun start() {
        //워커 스레드 생성
        timer = timer(initialDelay = 0, period = 100) {
            if (currentCountdownDeciSecond == 0) {
                currentDeciSecond += 1
                // Log.d(TAG, "start: $currentDeciSecond")
                val minutes = currentDeciSecond.div(10) / 60
                val seconds = currentDeciSecond.div(10) % 60
                val deciSeconds = currentDeciSecond % 10

                // 워커 스레드에서 main 스레드(ui 업데이트를 위해해) 로 보내는 handler!
                mHandler.post {
                    binding.timeTextView.text = String.format("%02d:%02d", minutes, seconds)
                    binding.tickTextView.text = deciSeconds.toString()
                    binding.countdownGroup.isVisible = false
                }
            } else {
                currentCountdownDeciSecond -= 1
                val second = currentCountdownDeciSecond / 10
                mHandler.post {
                    binding.countdownTextView.text = String.format("%02d", second)
                    binding.countdownProgressBar.progress =
                        ((currentCountdownDeciSecond / (countdownSecond * 10f)) * 100).toInt()

                }
            }
            //3초 카운트다운부터 beep 음
            if (currentDeciSecond == 0 && currentCountdownDeciSecond < 31 && currentCountdownDeciSecond % 10 == 0) {
                val toneType = if(currentCountdownDeciSecond == 0 ) ToneGenerator.TONE_CDMA_HIGH_L else ToneGenerator.TONE_CDMA_ANSWER
                ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME).startTone(toneType,100)
            }
        }

    }

    private fun stop() {
        currentDeciSecond = 0
        binding.timeTextView.text = "00:00"
        binding.tickTextView.text = "0"
        binding.countdownGroup.isVisible = true
        initCountdownView()
        binding.lapLinear.removeAllViews()

    }

    private fun pause() {
        timer?.cancel() // 타이머 끝내기
        timer = null
    }

    private fun lap() {
        if (currentDeciSecond == 0) return
        val container = binding.lapLinear
        TextView(requireContext()).apply {
            textSize = 20f
            gravity = Gravity.CENTER
            val minutes = currentDeciSecond.div(10) / 60
            val seconds = currentDeciSecond.div(10) % 60
            val deciSeconds = currentDeciSecond % 10
            //inc 1 증가        몇번째 랩인지 작성  형식 1. 01:03 0
            text = "${container.childCount.inc()}. ${
                String.format(
                    "%02d:%02d %01d",
                    minutes,
                    seconds,
                    deciSeconds
                )
            }"
            setPadding(30)
        }.let {
            container.addView(it, 0)
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setMessage("스탑워치를 중지하시겠습니까?")
            setPositiveButton("네") { _, _ ->
                stop()
            }
            setNegativeButton("아니요", null)
        }.show()
    }
}