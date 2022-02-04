package com.diary.someday.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diary.someday.model.network.dto.request.user.EmailSend
import com.diary.someday.model.network.dto.request.user.EmailSendCheck
import com.diary.someday.model.network.dto.request.user.SignUp
import com.diary.someday.model.repository.SignUpRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.regex.Pattern

class SignUpViewModel(private val repo: SignUpRepository) : ViewModel() {
    val emailSendCode = MutableLiveData<Int>()
    val emailSendCheckCode = MutableLiveData<Int>()
    val signUpCode = MutableLiveData<Int>()

    private val emailValidation =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    private val _buttonState = MutableLiveData<Boolean>()
    val buttonState: LiveData<Boolean>
        get() = _buttonState

    private val _buttonStateSignUp = MutableLiveData<Boolean>()
    val buttonStateSignUp: LiveData<Boolean>
        get() = _buttonStateSignUp

    init {
        _buttonState.value = false
        _buttonStateSignUp.value = false
    }

    private var pwd1Type = TYPE.PWD1FAIL
    private var pwd2Type = TYPE.PWD2FAIL


    private var emailType = TYPE.EMAILFAIL
    private var sendType = TYPE.SENDFAIL

    fun signUp(signUp: SignUp) {
        repo.signUp(signUp).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                signUpCode.postValue(it.body()?.code)
            }, {
                Log.d("Error", it.message.toString())
            })
    }

    fun emailSend(emailSend: EmailSend) {
        repo.emailSend(emailSend).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                emailSendCode.postValue(it.body()?.code)
            }, {
                Log.d("Error", it.message.toString())
            })
    }

    fun emailSendCheck(emailSendCheck: EmailSendCheck) {
        repo.emailSendCheck(emailSendCheck).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                emailSendCheckCode.postValue(it.body()?.code)
            }, {
                Log.d("Error", it.message.toString())
            })
    }

    fun checkEmailPattern(email: String) {
        val pattern = Pattern.matches(emailValidation, email) // 서로 패턴이 맞닝?
        if (pattern) {
            emailType = TYPE.EMAILOK
            _buttonState.value = checkAll()
        } else {
            emailType = TYPE.EMAILFAIL
            checkButtonState()
        }
    }

    fun checkCode(send: String) {
        if (send.length >= 6) {
            sendType = TYPE.SENDOK
            _buttonState.value = checkAll()
        } else {
            sendType = TYPE.SENDFAIL
            checkButtonState()
        }
    }

    fun checkPasswordLength(pwd1: String) {
        if (pwd1.length >= 4) {
            pwd1Type = TYPE.PWD1OK
            _buttonStateSignUp.value = checkAllPwd()
        }else{
            pwd1Type = TYPE.PWD1FAIL
            checkButtonStateSignUp()
        }
    }

    fun checkPasswordSame(pwd1:String, pwd2:String){
        if (pwd1.equals(pwd2)){
            pwd2Type = TYPE.PWD2OK
            _buttonStateSignUp.value = checkAllPwd()
        }else{
            pwd2Type = TYPE.PWD2FAIL
            checkButtonStateSignUp()
        }
    }

    private fun checkAllPwd(): Boolean = pwd1Type == TYPE.PWD1OK && pwd2Type == TYPE.PWD2OK

    private fun checkAll(): Boolean = sendType == TYPE.SENDOK && emailType == TYPE.EMAILOK

    private fun checkButtonStateSignUp(){
        if (_buttonStateSignUp.value == true) _buttonStateSignUp.value = false
    }

    private fun checkButtonState() {
        if (_buttonState.value == true) _buttonState.value = false
    }

}