package com.ian.fastcam.chap7

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ian.fastcam.R
import com.ian.fastcam.TAG
import com.ian.fastcam.chap7.db.AppDatabase
import com.ian.fastcam.databinding.Chap7Binding
import java.nio.file.Files.delete

class Vocabulary : Fragment(), WordAdapter.ItemClickListener {
    private lateinit var binding: Chap7Binding
    private lateinit var wordAdapter: WordAdapter
    private var selectedWord: Word? = null
    val dummyList = mutableListOf<Word>(
        Word("weather", "날씨", "명사"),
        Word("honey", "꿀", "명사"),
        Word("run", "실행하다", "동사")

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

        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_vocabulary_to_vocaAddFragment)
        }

        binding.deleteBtn.setOnClickListener {
            deleteWord()
        }

        binding.editBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_vocabulary_to_vocaAddFragment, bundleOf(
                    Pair("update", selectedWord)
                )
            )
        }
    }

    private fun initRecyclerView() {
        wordAdapter = WordAdapter(mutableListOf(), this)
        binding.wordRecyclerView.apply {
            adapter = wordAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration) // 와 라인 추가가 이렇게 되는 거엿군 .... item Bottom line 추가

        }

        Thread {
            val list = AppDatabase.getInstance(requireContext())?.wordDao()?.getAll() ?: emptyList()
            wordAdapter.list.addAll(list)
            Handler(Looper.getMainLooper()).post {
                wordAdapter.notifyDataSetChanged()
            }
        }.start()
    }

    private fun deleteWord() {
        if (selectedWord == null) return
        Thread {
            selectedWord?.let { word ->
                AppDatabase.getInstance(requireContext())?.wordDao()?.delete(word)
                Handler(Looper.getMainLooper()).post {
                    wordAdapter.list.remove(word)
                    wordAdapter.notifyDataSetChanged()
                    binding.textTextView.text = ""
                    binding.meanTextView.text = ""
                    Toast.makeText(context, "삭제 완료되었습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()

    }

    override fun onClick(word: Word) {
        selectedWord = word
        binding.textTextView.text = word.text
        binding.meanTextView.text = word.mean

    }
}