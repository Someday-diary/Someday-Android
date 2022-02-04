package com.diary.someday.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.diary.someday.R
import com.diary.someday.viewModel.SignUpViewModel
import com.diary.someday.databinding.FragmentSignUpPwdBinding
import com.diary.someday.model.network.dto.request.user.SignUp
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpPwdFragment : Fragment() {
    lateinit var binding: FragmentSignUpPwdBinding
    private val signUpViewModel: SignUpViewModel by viewModel()
    private val args: SignUpPwdFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpPwdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textWatcher()
        bindingButton()
        observe()
    }

    private fun textWatcher() {
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                signUpViewModel.checkPasswordLength(text.toString())
            }

        })
        binding.passwordCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                signUpViewModel.checkPasswordSame(binding.password.text.toString(), text.toString())
            }

        })
    }

    private fun bindingButton() {
        binding.viewTerms.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW)
            val uri: Uri = Uri.parse("https://someday-diary.notion.site/855a03b33c1f4f5ba32dfa63c09cab67")
            intent.data = uri
            startActivity(intent)
        }

        binding.checkBoxLayout.setOnClickListener {
            binding.checkbox.isChecked = !binding.checkbox.isChecked
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpPwdFragment_to_signUpFragment)
        }

        binding.signinButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpPwdFragment_to_signInFragment)
        }

        binding.buttonSignup.setOnClickListener {
            val agree = checkAgree(binding.checkbox.isChecked)
            val email = args.email
            signUpViewModel.signUp(SignUp(email, binding.password.text.toString(), agree))
        }
    }

    private fun observe() {
        signUpViewModel.buttonStateSignUp.observe(activity as LifecycleOwner, Observer {
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

        signUpViewModel.signUpCode.observe(viewLifecycleOwner, {
            when (it) {
                200 -> {
                    Toast.makeText(activity, "가입 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signUpPwdFragment_to_signInFragment)
                }
                104 -> {
                    Toast.makeText(activity, "이메일 인증이 되지 않은 계정입니다.", Toast.LENGTH_SHORT).show()
                }
                105 -> {
                    Toast.makeText(activity, "이미 가입된 계정입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun checkAgree(agree: Boolean): String = when (agree) {
        true -> "Y"
        false -> "N"
    }
}