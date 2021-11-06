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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.diary.someday.R
import com.diary.someday.application.Application
import com.diary.someday.databinding.FragmentCheckPwdBinding
import com.diary.someday.viewModel.CheckPwdViewModel

class CheckPwdFragment : Fragment() {

    private lateinit var binding: FragmentCheckPwdBinding
    private lateinit var checkPwdViewModel: CheckPwdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckPwdBinding.inflate(inflater, container, false)
        checkPwdViewModel = ViewModelProvider(this).get(CheckPwdViewModel::class.java)
        if (Application.lockState.getLockMode() == 2) {
            binding.lockText.text = "비밀번호를 입력하세요."
        }

        binding.toolbar.setNavigationOnClickListener {
            if (Application.lockState.getLockMode() == 2) {
            } else {
                findNavController().popBackStack()
            }
        }

        checkPwdViewModel.checkState.observe(activity as LifecycleOwner, {
            Log.d("TAG", "checkState 호출 $it\n\n")
            if (it) {
                if (Application.lockState.getLockMode() == 2) {
                    Toast.makeText(activity, "인증되었습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                Application.switchState.settingAll()
            } else {
                binding.lockText.text = "다시 입력해주세요."
                Toast.makeText(activity, "잘못 입력했습니다.", Toast.LENGTH_SHORT).show()
                checkPwdViewModel.reset()
            }
        })

        checkPwdViewModel.num1State.observe(activity as LifecycleOwner, {
            Log.d("TAG", "num1State 호출 $it\n\n")
            if (it) {
                changeCircle(1)
            } else {
                changeCircle(0)
            }
        })



        checkPwdViewModel.num2State.observe(activity as LifecycleOwner, {
            Log.d("TAG", "num2State 호출 $it\n\n")
            if (it) {
                changeCircle(2)
            } else {
                changeCircle(1)
            }
        })

        checkPwdViewModel.num3State.observe(activity as LifecycleOwner, {
            Log.d("TAG", "num3State 호출 $it\n\n")
            if (it) {
                changeCircle(3)
            } else {
                changeCircle(2)
            }
        })

        checkPwdViewModel.num4State.observe(activity as LifecycleOwner, {
            Log.d("TAG", "num4State 호출 $it\n\n")
            checkPwdViewModel.checkLockNumber()
        })

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
        checkPwdViewModel.addNumber(num)
    }

    fun remove() {
        checkPwdViewModel.deleteNumber()
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