package com.diary.someday.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.diary.someday.Enum.ResponseState
import com.diary.someday.R
import com.diary.someday.Retrofit.RetrofitManager
import com.diary.someday.Viewmodel.SignUpViewModel
import com.diary.someday.activity.MainActivity
import com.diary.someday.application.Application
import com.diary.someday.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        if (Application.signInCheck.checkSignIn()) {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
//            val intent = Intent(requireContext(), MainActivity::class.java)
//            startActivity(intent)
        } else if (Application.signUpCheck.checkSignUp()) {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        signUpViewModel.buttonState.observe(activity as LifecycleOwner, Observer {
            if (it == true) {
                binding.buttonSignup.setBackgroundResource(R.drawable.ic_login_button_abled)
                binding.buttonSignup.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.buttonSignup.isEnabled = true
            } else {
                binding.buttonSignup.setBackgroundResource(R.drawable.ic_login_button_enabled)
                binding.buttonSignup.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white_dark
                    )
                )
                binding.buttonSignup.isEnabled = false
            }
        })

        binding.buttonSignup.setOnClickListener {
            Log.d("TAG", "버튼 클릭")
            RetrofitManager.instance.emailSendCheck(
                binding.email.text.toString(),
                binding.sendNumber.text.toString()
            ) { responseState, code ->
                Log.d("TAG", "서버 결과 -> $responseState $code")
                when (responseState) {
                    ResponseState.OKAY -> {
                        Toast.makeText(activity, "인증완료 되었습니다.", Toast.LENGTH_SHORT).show()
                        var action =
                            SignUpFragmentDirections.actionSignUpFragmentToSignUpPwdFragment(binding.email.text.toString())
                        findNavController().navigate(action)
                    }
                    ResponseState.FAIL -> {
                        Toast.makeText(activity, "$code", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                signUpViewModel.checkEmail(text.toString())
            }

        })

        binding.sendNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                signUpViewModel.checkSend(text.toString())
            }

        })
        binding.emailSendButton.setOnClickListener {
            RetrofitManager.instance.emailSend(
                binding.email.text.toString()
            ) { responseState, code ->
                when (responseState) {
                    ResponseState.OKAY -> {
                        Toast.makeText(activity, "이메일의 인증번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    ResponseState.FAIL -> {
                        Toast.makeText(activity, "$code", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.signinButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        return binding.root
    }
}