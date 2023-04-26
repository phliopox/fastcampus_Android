package com.ian.fastcam.chap4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ian.fastcam.R
import com.ian.fastcam.databinding.Chap41Binding

class EmergencyMedical : Fragment() {
    private lateinit var binding : Chap41Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap41Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editBtn.setOnClickListener{
            findNavController().navigate(R.id.action_emergencyMedical_to_editMedicalInfo)
        }
    }
}