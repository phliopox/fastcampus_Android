package com.ian.fastcam.chap9

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ian.fastcam.MEDIA_PLAYER_PAUSE
import com.ian.fastcam.MEDIA_PLAYER_PLAY
import com.ian.fastcam.MEDIA_PLAYER_STOP
import com.ian.fastcam.R
import com.ian.fastcam.databinding.Chap9Binding


class MusicFragment : Fragment() {

    private lateinit var binding: Chap9Binding
   // private var mediaPlayer: MediaPlayer? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap9Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startBtn.setOnClickListener { play() }
        binding.stopBtn.setOnClickListener { stop() }
        binding.pauseBtn.setOnClickListener { pause() }
    }

    private fun play() {
        /* service 포그라운드 작업 (백그라운드 작업 중 사용자에게 ui 가 보여야하는 기능들) 을 수행하기 위해 intent*/
        val intent = Intent(context,MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_PLAY }
        requireActivity().startService(intent)

    /* 다른 앱 동작시에도 음악 플레이하는 기능이 필요없다면 단순히 아래 로직으로 play 가능*/
    /*  if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.dream_of_octopus)
        }
        mediaPlayer?.start()
*/
    }

    private fun stop() {
        val intent = Intent(context,MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_STOP }
        requireActivity().startService(intent)

        //mediaPlayer?.stop()
    }

    private fun pause() {
        val intent = Intent(context,MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_PAUSE }
        requireActivity().startService(intent)


    /*     mediaPlayer?.apply {
            pause()
            release()
        }
        mediaPlayer = null*/
    }

    override fun onDestroy() {
        /*서비스 중지시켜주기 */
        requireActivity().stopService(Intent(requireContext(),MediaPlayerService::class.java))
        super.onDestroy()
    }
}