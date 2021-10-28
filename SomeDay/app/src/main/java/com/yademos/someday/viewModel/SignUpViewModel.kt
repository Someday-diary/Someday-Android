package com.yademos.someday.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {
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

    fun checkEmail(email: String) {
        val pattern = Pattern.matches(emailValidation, email) // 서로 패턴이 맞닝?
        if (pattern) {
            emailType = TYPE.EMAILOK
            _buttonState.value = checkAll()
        } else {
            emailType = TYPE.EMAILFAIL
            checkButtonState()
        }
    }

    fun checkSend(send: String) {
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