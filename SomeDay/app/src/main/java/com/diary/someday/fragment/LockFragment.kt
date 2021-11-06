package com.yademos.someday.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_WEAK
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.diary.someday.R
import com.diary.someday.application.Application
import com.diary.someday.databinding.FragmentLockBinding
import java.util.concurrent.Executor

class LockFragment : Fragment() {

    private lateinit var binding: FragmentLockBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockBinding.inflate(inflater, container, false)
        if (Application.switchState.getSettingAll()){
            binding.switchLock.isChecked = true
        }

        executor = ContextCompat.getMainExecutor(activity as Context)
        biometricPrompt = BiometricPrompt(
            requireActivity(), executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(activity as Context, "성공했어요.", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("생체 정보로 인증해주세요")
            .setNegativeButtonText("비밀번호로 인증")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or BIOMETRIC_WEAK)
            .build()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.changePassword.setOnClickListener {

        }

        binding.switchBiometric.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                if (biometricsAvailable()) {
                    biometricPrompt.authenticate(promptInfo)
                }
            }
        }

        binding.switchLock.setOnCheckedChangeListener { compoundButton, isChecked ->
            val switchAction = Application.switchState.getCanecelNav()
            val settingAll = Application.switchState.getSettingAll()
            if (isChecked) {
                if (switchAction) {
                    findNavController().navigate(R.id.action_lockFragment_to_editPwdFragment)
                } else if(settingAll){
                    binding.switchBiometric.isEnabled = true
                    binding.changePassword.isEnabled = true
                    Application.switchState.delete()
                    Toast.makeText(activity,"비밀번호 설정을 완료하였습니다.",Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "${switchAction}")
                } else{
                    Application.switchState.delete()
                    binding.switchLock.isChecked = false
                    binding.switchBiometric.isChecked = false
                    binding.switchBiometric.isEnabled = false
                    binding.changePassword.isEnabled = false
                }
            } else {
                binding.switchBiometric.isChecked = false
                binding.switchBiometric.isEnabled = false
                binding.changePassword.isEnabled = false
            }
        }
        return binding.root
    }

    @SuppressLint("WrongConstant")
    private fun biometricsAvailable(): Boolean {
        val biometricManager = BiometricManager.from(activity as Context)
        return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("TAG", "생체 인증을 사용할 수 있습니다.")
                true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.e("TAG", "하드웨어가 없습니다.")
                false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.e("TAG", "생체 인증을 사용할 수 없습니다.")
                false
            }
            else -> false
        }
    }
}