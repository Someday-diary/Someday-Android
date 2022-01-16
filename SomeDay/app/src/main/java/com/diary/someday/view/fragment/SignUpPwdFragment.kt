package com.diary.someday.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.diary.someday.model.network.util.Enum.ResponseState
import com.diary.someday.R
import com.diary.someday.model.network.RetrofitManager
import com.diary.someday.Viewmodel.SignUpViewModel
import com.diary.someday.application.Application
import com.diary.someday.databinding.FragmentSignUpPwdBinding

class SignUpPwdFragment : Fragment() {
    lateinit var binding: FragmentSignUpPwdBinding
    private val signUpViewModel: SignUpViewModel by viewModels()
    val args: SignUpPwdFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpPwdBinding.inflate(inflater, container, false)
//        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)




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

        binding.buttonSignup.setOnClickListener {
            val agree = checkAgree(binding.checkbox.isChecked)
            val email = args.email
            Log.d("TAG", "이메일 $email")
            RetrofitManager.instance.signUp(
                email,
                binding.password.text.toString(),
                agree
            ) { responseState, code ->
                Log.d("TAG", "서버 결과 -> $responseState $code")
                when (responseState) {
                    ResponseState.OKAY -> {
                        Application.signUpCheck.signUp()
                        findNavController().navigate(R.id.action_signUpPwdFragment_to_signInFragment)
                    }
                    ResponseState.FAIL -> {
                        Toast.makeText(activity, "$code", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



        return binding.root
    }

    fun checkAgree(agree: Boolean): String = when (agree) {
        true -> "Y"
        false -> "N"
    }
}