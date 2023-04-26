package com.ian.fastcam.chap4

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
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

        getPrefInfoAndUpdate()

        binding.editBtn.setOnClickListener{
            findNavController().navigate(R.id.action_emergencyMedical_to_editMedicalInfo)
        }

        binding.deleteBtn.setOnClickListener {
            deleteUserInfo()
        }

    }
    private fun getPrefInfoAndUpdate(){
        val pref = context?.getSharedPreferences(USER_INFO_KEY, Context.MODE_PRIVATE)
        pref?.let{
            with(it) {
                binding.nameValue.text = getString(NAME, "미정")
                binding.bloodTypeValue.text = getString(BLOOD_TYPE, "미정")
                binding.phoneValue.text = getString(PHONE, "미정")
                binding.birthValue.text = getString(BIRTH_DATE, "미정")

                val caution = getString(CAUTION, "")
                binding.cautionLabel.isVisible = !caution.isNullOrEmpty()
                binding.cautionValue.isVisible = !caution.isNullOrEmpty()

                if(!caution.isNullOrEmpty()){
                    binding.cautionValue.text = getString(CAUTION, "")
                }
            }
        }
    }

    private fun deleteUserInfo(){
        val pref = context?.getSharedPreferences(USER_INFO_KEY, Context.MODE_PRIVATE)
        pref?.edit()?.clear()?.apply()
        getPrefInfoAndUpdate()
        Toast.makeText(context,"초기화를 완료했습니다.",Toast.LENGTH_SHORT).show()
    }
}