package com.diary.someday.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.diary.someday.model.network.util.constants.PWD_TYPE
import com.diary.someday.R
import com.diary.someday.di.application.Application
import com.diary.someday.databinding.FragmentEditPwdBinding
import com.diary.someday.viewModel.EditPwdViewModel
import java.util.concurrent.Executor

class EditPwdFragment : Fragment() {
    private lateinit var binding: FragmentEditPwdBinding
    private val editPwdViewModel: EditPwdViewModel by viewModels()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var backKeyPressedTime: Long = 0
    private lateinit var callBack: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Application.switchState.cancelNav()
        if (Application.switchState.getBioSwitch()) {
            if (Application.lockNumber.getAddType() == PWD_TYPE.CHECK_LOCK_MAIN) {
                biometric()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditPwdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changePassword()
        bindingToolbar()
        observe()
        numberButton()
    }

    private fun changePassword() {
        if (Application.lockNumber.getAddType() == PWD_TYPE.CHANGE_LOCK) {
            binding.lockText.text = "기존의 비밀번호를 입력하세요."
        }
    }

    private fun bindingToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            when (Application.lockNumber.getAddType()) {
                PWD_TYPE.ENABLE_LOCK -> {
                    findNavController().popBackStack()
                }
                PWD_TYPE.CHECK_LOCK -> {
                    Application.lockNumber.addType(PWD_TYPE.ENABLE_LOCK)
                    editPwdViewModel.reset()
                    binding.lockText.text = "비밀번호를 입력하세요."
                }
                PWD_TYPE.CHECK_LOCK_MAIN -> {
                    Toast.makeText(activity, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun observe() = with(editPwdViewModel) {

        num1State.observe(activity as LifecycleOwner) {
            changeCircle(1)
        }

        num2State.observe(activity as LifecycleOwner) {
            changeCircle(2)
        }

        num3State.observe(activity as LifecycleOwner) {
            changeCircle(3)
        }

        num4State.observe(activity as LifecycleOwner) {
            when (Application.lockNumber.getAddType()) {
                PWD_TYPE.ENABLE_LOCK -> {
                    editPwdViewModel.setPreference()
                    editPwdViewModel.reset()
                    changeCircle(0)
                }
                PWD_TYPE.CHECK_LOCK -> {
                    editPwdViewModel.checkPreference()
                    editPwdViewModel.reset()
                    changeCircle(0)
                }
                PWD_TYPE.CHANGE_LOCK -> {
                    editPwdViewModel.checkPreference()
                    editPwdViewModel.reset()
                    changeCircle(0)
                }
                PWD_TYPE.CHECK_LOCK_MAIN -> {
                    editPwdViewModel.checkPreference()
                    editPwdViewModel.reset()
                    changeCircle(0)
                }
            }
        }

        num1StateDelete.observe(activity as LifecycleOwner) {
            changeCircle(0)
        }

        num2StateDelete.observe(activity as LifecycleOwner) {
            changeCircle(1)
        }

        num3StateDelete.observe(activity as LifecycleOwner) {
            changeCircle(2)
        }

        setAll.observe(activity as LifecycleOwner) {
            Application.switchState.settingAll()
            findNavController().popBackStack()
        }

        changeText.observe(activity as LifecycleOwner) {
            binding.lockText.text = it
        }

        changeTextError.observe(activity as LifecycleOwner) {
            binding.lockText.text = it
        }

        check.observe(activity as LifecycleOwner) {
            binding.lockText.text = "비밀번호를 입력하세요."
            changeCircle(0)
        }

        checkMain.observe(activity as LifecycleOwner) {
            Toast.makeText(activity, "인증했습니다.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun numberButton() {
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
    }

    private fun add(num: Int) {
        editPwdViewModel.addNumber(num)
    }

    private fun remove() {
        editPwdViewModel.deleteNumber()
    }

    private fun changeCircle(num: Int) {
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

    private fun biometric() {
        executor = ContextCompat.getMainExecutor(activity as Context)
        biometricPrompt = BiometricPrompt(
            requireActivity(), executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    editPwdViewModel.biometric()
                    Application.lockNumber.done()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("생체 정보로 인증해주세요")
            .setNegativeButtonText("비밀번호로 인증")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (Application.lockNumber.getAddType() == PWD_TYPE.CHECK_LOCK_MAIN) {
                    if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                        backKeyPressedTime = System.currentTimeMillis()
                        Toast.makeText(activity, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                    if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
                        activity?.moveTaskToBack(true)
                        activity?.finishAndRemoveTask()
                        android.os.Process.killProcess(android.os.Process.myPid())
                    }
                } else {
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)
    }

    override fun onDetach() {
        super.onDetach()
        callBack.remove()
    }
}