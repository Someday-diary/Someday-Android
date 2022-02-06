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
import androidx.navigation.fragment.findNavController
import com.diary.someday.R
import com.diary.someday.viewModel.SignInViewModel
import com.diary.someday.view.activity.MainActivity
import com.diary.someday.di.application.Application
import com.diary.someday.databinding.FragmentSignInBinding
import com.diary.someday.model.network.dto.request.user.SignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {

    private val signInViewModel: SignInViewModel by viewModel()
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        changeModeColor(Application.themeSettingColor.getThemeTypeColor())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingButton()
        errorCheck()
        loginCheck()
        observe()
    }

    private fun observe() {
        signInViewModel.buttonState.observe(activity as LifecycleOwner, Observer {
            changedButton(it)
        })
        signInViewModel.code.observe(viewLifecycleOwner, {
            when (it) {
                200 -> {
                    startActivity(Intent(activity, MainActivity::class.java))
                }
                106 -> {
                    Toast.makeText(activity, "유저 정보가 없는 계정입니다", Toast.LENGTH_SHORT).show()
                }
                107 -> {
                    Toast.makeText(activity, "이메일 인증이 되지 않은 계정입니다.", Toast.LENGTH_SHORT).show()
                }
                108 -> {
                    Toast.makeText(activity, "이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun bindingButton() {
        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.buttonSignin.setOnClickListener {
            Log.d("TAG", "버튼 클릭")
            signInViewModel.signIn(SignIn(binding.email.text.toString(), binding.password.text.toString()))
        }
    }

    private fun loginCheck() {
        binding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = binding.email.text.toString()
                signInViewModel.checkEmail(email)
            }
        })

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.password.text.toString()
                signInViewModel.checkPassword(password)
            }
        })
    }

    private fun errorCheck() {
        signInViewModel.emailError.observe(activity as LifecycleOwner, Observer {
            binding.emailErrorText.text = it.toString()
        })

        signInViewModel.passwordError.observe(activity as LifecycleOwner, Observer {
            binding.passwordErrorText.text = it.toString()
        })
    }

    private fun changeModeColor(number: Int) {
        Log.d("TAG", "changeModeColor: number $number")

        when (number) {
            1 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_icon_green
                    )
                )
                binding.gotoSignUp.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.green3
                    )
                )
            }
            2 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_icon_blue
                    )
                )
                binding.gotoSignUp.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.blue3
                    )
                )
            }
            3 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_icon_purple
                    )
                )
                binding.gotoSignUp.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.purple3
                    )
                )
            }
            4 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_icon_yellow
                    )
                )
                binding.gotoSignUp.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.yellow3
                    )
                )
            }
            5 -> {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity as Context,
                        R.drawable.ic_icon_red
                    )
                )
                binding.gotoSignUp.setTextColor(
                    ContextCompat.getColor(
                        activity as Context,
                        R.color.red3
                    )
                )
            }
        }
    }

    private fun changedButton(check: Boolean) {
        if (check) {
            when (Application.themeSettingColor.getThemeTypeColor()) {
                1 -> {
                    binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled_green)
                }
                2 -> {
                    binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled_blue)
                }
                3 -> {
                    binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled_purple)
                }
                4 -> {
                    binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled_yellow)
                }
                5 -> {
                    binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_abled_red)
                }
            }
            binding.buttonSignin.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.buttonSignin.isEnabled = true
        } else {
            binding.buttonSignin.setBackgroundResource(R.drawable.ic_login_button_enabled)
            binding.buttonSignin.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_color
                )
            )
            binding.buttonSignin.isEnabled = false
        }
    }
}