package com.ian.fastcam.chap7

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.ian.fastcam.chap7.db.AppDatabase
import com.ian.fastcam.databinding.Chap7AddBinding

class VocaAddFragment : Fragment() {
    private lateinit var binding: Chap7AddBinding
    private var originWord: Word? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap7AddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        arguments?.getParcelable<Word>("update")?.let { word ->
            originWord = word
            binding.editText.setText(word.text)
            binding.meanEditText.setText(word.mean)
            binding.addBtn.text = "수정"
            binding.typeChipGroup.children.toList().forEach {
                if ((it as Chip).text == word.type) {
                    it.isChecked = true
                }
            }

        }
        binding.addBtn.setOnClickListener {
            if (originWord == null) {
                addWord()
            } else {
                editWord()
            }
        }
    }

    private fun initViews() {
        val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")

        binding.typeChipGroup.apply {
            types.forEach { text ->
                addView(createChip(text))
            }
        }

        binding.editText.addTextChangedListener {
            it?.let{ text->
                binding.textInputLayout.error =
                when (text.length) {
                    0 -> "값을 입력해주세요"
                    1 -> "2자 이상을 입력해주세요"
                    else -> null

                }
            }
        }
    }

    private fun createChip(text: String): Chip {
        return Chip(requireContext()).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }

    private fun addWord() {
        val text = binding.editText.text.toString()
        val mean = binding.meanEditText.text.toString()
        /* var ss = ""
         val type = binding.typeChipGroup.children.toList().filter {(it as Chip).isChecked}.forEach {
             ss = (it as Chip).text.toString()
             (it as Chip).text.toString()
         }*/
        val typeText =
            requireActivity().findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()

        val newWord = Word(text, mean, typeText)
        //AppDatabase.getInstance(requireContext())?.wordDao()?.insert(newWord)
        // Toast.makeText(context,"저장완료",Toast.LENGTH_SHORT).show()
        Thread {
            AppDatabase.getInstance(requireContext())?.wordDao()?.insert(newWord)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }.start()
    }

    private fun editWord() {
        val text = binding.editText.text.toString()
        val mean = binding.meanEditText.text.toString()
        val typeText =
            requireActivity().findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val editWord = originWord?.copy(text = text, mean = mean, type = typeText) //깊은 복사 새로운 객체 생성 x
        Thread {
            editWord?.let {
                AppDatabase.getInstance(requireContext())?.wordDao()?.update(editWord)
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "수정 완료", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }.start()
    }
}