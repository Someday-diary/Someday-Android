package com.diary.someday.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
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
import com.diary.someday.R
import com.diary.someday.viewModel.SignUpViewModel
import com.diary.someday.databinding.FragmentSignUpBinding
import com.diary.someday.model.network.dto.request.user.EmailSend
import com.diary.someday.model.network.dto.request.user.EmailSendCheck
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textObserve()
        bindingButton()
        observe()
    }

    private fun textObserve() {

        binding.email.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(text: Editable?) {
                    signUpViewModel.checkEmailPattern(text.toString())
                }
            })

        binding.sendNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(text: Editable?) {
                    signUpViewModel.checkCode(text.toString())
                }
            })
        }

    private fun bindingButton() {
        binding.buttonSignup.setOnClickListener {
            signUpViewModel.emailSendCheck(EmailSendCheck(binding.email.text.toString(), binding.sendNumber.text.toString()))
        }

        binding.emailSendButton.setOnClickListener {
            signUpViewModel.emailSend(EmailSend(binding.email.text.toString()))
        }

        binding.signinButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun observe() {
        signUpViewModel.emailSendCheckCode.observe(viewLifecycleOwner, {
            when(it) {
                200 -> {
                    Toast.makeText(activity, "인증완료 되었습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignUpPwdFragment(binding.email.text.toString()))
                }
                102 -> Toast.makeText(activity, "유효한 인증 번호가 없습니다. 다시 요청해주세요.", Toast.LENGTH_SHORT).show()
                103 -> Toast.makeText(activity, "코드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        signUpViewModel.emailSendCode.observe(viewLifecycleOwner, {
            when(it) {
                200 -> Toast.makeText(activity, "이메일을 확인해주세요.", Toast.LENGTH_SHORT).show()
                101 -> Toast.makeText(activity, "이미 가입되어 있는 이메일입니다.", Toast.LENGTH_SHORT).show()
            }
        })

        signUpViewModel.buttonState.observe(activity as LifecycleOwner, Observer {
            if (it == true) {
                binding.buttonSignup.setBackgroundResource(R.drawable.ic_login_button_abled_green)
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
    }
}