package com.ian.fastcam.chap7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ian.fastcam.TAG
import com.ian.fastcam.databinding.Chap7Binding

class Vocabulary : Fragment() ,WordAdapter.ItemClickListener{
    private lateinit var binding: Chap7Binding
    private lateinit var wordAdapter: WordAdapter
    val dummyList = mutableListOf<Word>(
        Word("weather","날씨","명사"),
        Word("honey","꿀","명사"),
        Word("run","실행하다","동사")

    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap7Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        wordAdapter = WordAdapter(dummyList, this)
        binding.wordRecyclerView.apply {
            adapter = wordAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration) // 와 라인 추가가 이렇게 되는 거엿군 .... item Bottom line 추가

        }
    }

    override fun onClick(word: Word) {
        Log.d(TAG, "Vocabulary - onClick: $word");
    }
}