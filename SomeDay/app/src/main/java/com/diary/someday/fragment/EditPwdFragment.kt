package com.yademos.someday.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.diary.someday.R
import com.diary.someday.application.Application
import com.diary.someday.databinding.FragmentEditPwdBinding
import com.diary.someday.viewModel.EditPwdViewModel

class EditPwdFragment : Fragment() {
    private lateinit var binding: FragmentEditPwdBinding
    private lateinit var editPwdViewModel: EditPwdViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Application.switchState.cancelNav()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditPwdBinding.inflate(inflater, container, false)
        editPwdViewModel = ViewModelProvider(this).get(EditPwdViewModel::class.java)

        if (Application.switchState.getSettingAll()) {
            findNavController().popBackStack()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        editPwdViewModel.num1State.observe(activity as LifecycleOwner, {
            if (it) {
                changeCircle(1)
            } else {
                changeCircle(0)
            }
        })



        editPwdViewModel.num2State.observe(activity as LifecycleOwner,{
            if (it){
                changeCircle(2)
            }else{
                changeCircle(1)
            }
        })

        editPwdViewModel.num3State.observe(activity as LifecycleOwner,{
            if (it){
                changeCircle(3)
            }else{
                changeCircle(2)
            }
        })

        editPwdViewModel.num4State.observe(activity as LifecycleOwner,{
            editPwdViewModel.setPreference()
            findNavController().navigate(R.id.action_editPwdFragment_to_checkPwdFragment)
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
        editPwdViewModel.addNumber(num)
    }

    fun remove() {
        editPwdViewModel.deleteNumber()
    }

    fun changeCircle(num: Int) {
        Log.d("TAG", "changeCircle: 동그라미 수 $num")
        when (num) {
            0 -> {
                binding.circle1.background = ContextCompat.getDrawable(
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