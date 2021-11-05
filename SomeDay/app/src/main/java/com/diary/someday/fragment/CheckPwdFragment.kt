package com.yademos.someday.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.yademos.someday.R
import com.yademos.someday.application.Application
import com.yademos.someday.databinding.FragmentCheckPwdBinding

class CheckPwdFragment : Fragment() {

    private lateinit var binding : FragmentCheckPwdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckPwdBinding.inflate(inflater,container,false)

        Application.lockNumberCheck.removeAll()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.number0.setOnClickListener { add(0) }

        binding.number1.setOnClickListener { add(1) }

        binding.number2.setOnClickListener { add(2) }

        binding.number3.setOnClickListener { add(3) }

        binding.number4.setOnClickListener { add(4) }

        binding.number5.setOnClickListener { add(5) }

        binding.number6.setOnClickListener { add(6) }

        binding.number7.setOnClickListener { add(7) }

        binding.number8.setOnClickListener { add(8) }

        binding.number9.setOnClickListener { add(9) }

        binding.eraseButton.setOnClickListener { remove() }

        return binding.root
    }

    fun add(num: Int) {
        Application.lockNumberCheck.addNumber(num)
        if (Application.lockNumberCheck.getSelectAll()) {
            Log.d("TAG", "add: lockNumberCheck 값 ${Application.lockNumberCheck.getNumberCheck()}")
            Log.d("TAG", "add: lockNumber 값 ${Application.lockNumber.getNumber()}")
            val lockNumberCheck = Application.lockNumberCheck.getNumberCheck()
            val lockNumber = Application.lockNumber.getNumber()
            if (lockNumber.equals(lockNumberCheck)){
                Application.switchState.settingAll()
                findNavController().popBackStack()
            }else{
                binding.lockText.text = "다시 입력해주세요."
                Toast.makeText(activity,"잘못 입력했습니다.",Toast.LENGTH_SHORT).show()
                Application.lockNumberCheck.removeAll()
                val circleNumber = Application.lockNumberCheck.getCircleChangeNumber()
                Log.d("TAG", "remove하고 난 후 : ${Application.lockNumberCheck.getCircleChangeNumber()}")
                changeCircle(circleNumber)
            }

        } else {
            val circleNumber = Application.lockNumberCheck.getCircleChangeNumber()
            changeCircle(circleNumber)
        }
    }

    fun remove() {
        Application.lockNumberCheck.removeNumber()
        val circleNumber = Application.lockNumberCheck.getCircleChangeNumber()
        changeCircle(circleNumber)
    }

    fun changeCircle(num: Int) {
        Log.d("TAG", "changeCircle: 동그라미 수 $num")
        when (num) {
            0 -> {
                binding.circle1.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.lock_circle_unselected
                )
                binding.circle2.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.lock_circle_unselected
                )
                binding.circle3.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.lock_circle_unselected
                )
                binding.circle4.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.lock_circle_unselected
                )
            }
            1 -> {
                binding.circle1.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.lock_circle_selected
                )
                binding.circle2.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.lock_circle_unselected
                )
            }
            2 -> {
                binding.circle2.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.lock_circle_selected
                )
                binding.circle3.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.lock_circle_unselected
                )
            }
            3 -> {
                binding.circle3.background = ContextCompat.getDrawable(
                    activity as Context,
                    R.drawable.lock_circle_selected
                )
            }
        }
    }
}