package com.ian.fastcam.chap7

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.UserDictionary.Words.addWord
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.ian.fastcam.R
import com.ian.fastcam.TAG
import com.ian.fastcam.chap7.db.AppDatabase
import com.ian.fastcam.databinding.Chap7AddBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class VocaAddFragment : Fragment() {
    private lateinit var binding : Chap7AddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap7AddBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        binding.addBtn.setOnClickListener {
            addWord()
        }
    }
    private fun initViews(){
        val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")

        binding.typeChipGroup.apply {
            types.forEach{text ->
                addView(createChip(text))
            }
        }
    }
    private fun createChip(text : String) : Chip{
        return Chip(requireContext()).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }
    private fun addWord(){
        val text = binding.editText.text.toString()
        val mean = binding.meanEditText.text.toString()
       /* var ss = ""
        val type = binding.typeChipGroup.children.toList().filter {(it as Chip).isChecked}.forEach {
            ss = (it as Chip).text.toString()
            (it as Chip).text.toString()
        }*/
        val typeText =
            requireActivity().findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()

        val newWord = Word(text,mean,typeText)
        //AppDatabase.getInstance(requireContext())?.wordDao()?.insert(newWord)
       // Toast.makeText(context,"저장완료",Toast.LENGTH_SHORT).show()
        Thread{
            AppDatabase.getInstance(requireContext())?.wordDao()?.insert(newWord)
            Handler(Looper.getMainLooper()).post{
                Toast.makeText(context,"저장완료",Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }.start()
    }
}