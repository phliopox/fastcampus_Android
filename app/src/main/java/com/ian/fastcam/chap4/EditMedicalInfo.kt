package com.ian.fastcam.chap4

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.ian.fastcam.databinding.Chap42Binding
import java.text.SimpleDateFormat
import java.util.*

class EditMedicalInfo : Fragment() {
    private lateinit var binding : Chap42Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap42Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.birthDateLayer.setOnClickListener{
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            datePicker.show(childFragmentManager,"")
            datePicker.addOnPositiveButtonClickListener {
                val format = SimpleDateFormat("yyyy-MM-dd",Locale.KOREA)
                val date = Date()
                date.time = it
                val selectedDate = format.format(date)
                binding.birthValue.text = selectedDate
            }
        }
        //자동 하이픈 추가
        binding.phoneValue.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        binding.cautionCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.cautionValue.isVisible = isChecked
        }
        binding.saveBtn.setOnClickListener{
            saveData()
            findNavController().navigateUp()
        }
    }
    private fun saveData(){
        val pref = context?.getSharedPreferences(USER_INFO_KEY, Context.MODE_PRIVATE)
        pref?.let {
            //val editor = pref.edit()
            with(it.edit()) {
                putString(NAME, binding.nameValue.text.toString())
                putString(BLOOD_TYPE, getBloodType())
                putString(PHONE, binding.phoneValue.text.toString())
                putString(BIRTH_DATE, binding.birthValue.text.toString())
                putString(CAUTION,getCaution())
                apply()
            }
            Toast.makeText(context,"저장을 완료했습니다",Toast.LENGTH_SHORT).show()
        }
    }
    private fun getBloodType():String{
        val bloodType = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if(binding.rhPlus.isChecked) "+" else "-"
        return "$bloodSign$bloodType"
    }
    private fun getCaution(): String{
        return if(binding.cautionCheckBox.isChecked) binding.cautionValue.text.toString() else ""
    }
}