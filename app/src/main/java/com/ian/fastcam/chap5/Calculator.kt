package com.ian.fastcam.chap5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ian.fastcam.databinding.Chap5Binding
import java.text.DecimalFormat


/**
 *
Activity 인경우 xml button attribute 인 android:onClick="" 을 이용해서 접근하면 쉽다. ( 맨 아래 아래 주석 처리 해서 코드 남겨두엇다)
fragment 단에서 일반 레이아웃은 View.onclickListener 를 implement 해 사용하면 쉽게 리스너를 구현 가능하지만,
constraint layout 의 flow 경우 group 으로 분류되는지, 바로 버튼에게 리스너를 먹이면 반응이 없다.
때문에 flow 에서 referenced id를 꺼내 하나씩 리스너를 달아줌!!

 */

class Calculator : Fragment() {
    private lateinit var binding: Chap5Binding
    //자주 변경되는 텍스트 -> StringBuilder 를 사용
    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")
    private val decimalFormat = DecimalFormat("#,###")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Chap5Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val keyPadFlow = binding.KeyPadFlow
        val referencedIds = keyPadFlow.referencedIds

        for (id in referencedIds) { // flow referenceIds 를 꺼내 리스너 달아주기
            binding.root.findViewById<Button>(id).setOnClickListener {
                keyPadClicked(it)
            }
        }
    }

    private fun keyPadClicked(view: View) {
        when (view) {
            binding.btnMinus -> {
                operator((view as Button).text.toString())
            }
            binding.btnPlus -> {
                operator((view as Button).text.toString())
            }
            binding.btnClear -> {
                firstNumberText.clear()
                secondNumberText.clear()
                operatorText.clear()
                binding.result.text = ""
                updateEquationTextView()
            }
            binding.btnEqual -> {
                if (firstNumberText.isEmpty() || secondNumberText.isEmpty() || operatorText.isEmpty()) {
                    Toast.makeText(context, "올바르지 않은 수식입니다", Toast.LENGTH_SHORT).show()
                    return
                }
                val firstNumber = firstNumberText.toString().toBigDecimal()
                val secondNumber = secondNumberText.toString().toBigDecimal()
                val result =
                    when (operatorText.toString()) {
                        "+" -> decimalFormat.format(firstNumber + secondNumber)
                        "-" -> decimalFormat.format(firstNumber - secondNumber)
                        else -> {
                            "올바르지 않은 수식입니다"
                        }
                    }
                binding.result.text = result

            }
            //숫자 버튼일 경우
            else -> {
                val numberString = (view as Button).text
                val numberText = if (operatorText.isEmpty()) firstNumberText else secondNumberText

                numberText.append(numberString)
                updateEquationTextView()
            }
        }
    }

    private fun operator(operator: String) {
        if (firstNumberText.isEmpty()) {
            Toast.makeText(context, "먼저 숫자를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (secondNumberText.isNotEmpty()) {
            Toast.makeText(context, "1개의 연산자에 대해서만 연산이 가능합니다", Toast.LENGTH_SHORT).show()
            return
        }
        if (operatorText.isNotEmpty()){
            return
        }

        this.operatorText.append(operator)
        updateEquationTextView()
    }

    //ui 업데이트
    private fun updateEquationTextView() {
        val firstFormattedNumber = if(firstNumberText.isNotEmpty()) decimalFormat.format(firstNumberText.toString().toBigDecimal()) else ""
        val secondFormattedNumber = if(secondNumberText.isNotEmpty()) decimalFormat.format(secondNumberText.toString().toBigDecimal()) else ""
        binding.equationTv.text = "$firstFormattedNumber $operatorText $secondFormattedNumber"

    }


    /* fun numberClicked(view: View) {

     }

     fun clearClicked(view: View) {

     }

     fun equalClicked(view: View) {

     }

     fun operatorClicked(view: View) {

     }*/

}