package com.diary.someday.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diary.someday.Constants.PWD_TYPE
import com.diary.someday.application.Application

class EditPwdViewModel : ViewModel() {
    private val _num1 = MutableLiveData<Int>()
    private val _num2 = MutableLiveData<Int>()
    private val _num3 = MutableLiveData<Int>()
    private val _num4 = MutableLiveData<Int>()

    private var _num1State = MutableLiveData<Boolean>()
    private var _num2State = MutableLiveData<Boolean>()
    private var _num3State = MutableLiveData<Boolean>()
    private var _num4State = MutableLiveData<Boolean>()

    val num1State: LiveData<Boolean>
        get() = _num1State
    val num2State: LiveData<Boolean>
        get() = _num2State
    val num3State: LiveData<Boolean>
        get() = _num3State
    val num4State: LiveData<Boolean>
        get() = _num4State

    private var _num1StateDelete = MutableLiveData<Boolean>()
    private var _num2StateDelete = MutableLiveData<Boolean>()
    private var _num3StateDelete = MutableLiveData<Boolean>()
    private var _num4StateDelete = MutableLiveData<Boolean>()

    val num1StateDelete: LiveData<Boolean>
        get() = _num1StateDelete
    val num2StateDelete: LiveData<Boolean>
        get() = _num2StateDelete
    val num3StateDelete: LiveData<Boolean>
        get() = _num3StateDelete
    val num4StateDelete: LiveData<Boolean>
        get() = _num4StateDelete

    private var _setAll = MutableLiveData<Boolean>()
    private var _check = MutableLiveData<Boolean>()
    private var _checkMain = MutableLiveData<Boolean>()

    val setAll: LiveData<Boolean>
        get() = _setAll
    val check: LiveData<Boolean>
        get() = _check
    val checkMain: LiveData<Boolean>
        get() = _checkMain

    private var _changeText = MutableLiveData<String>()
    private var _changeTextError = MutableLiveData<String>()

    val changeText: LiveData<String>
        get() = _changeText
    val changeTextError: LiveData<String>
        get() = _changeTextError

    init {
        _num1.value = -1
        _num2.value = -1
        _num3.value = -1
        _num4.value = -1
    }

    fun addNumber(num: Int) {
        if (_num3.value != -1) {
            _num4.value = num
            _num4State.value = true
        } else if (_num2.value != -1) {
            _num3.value = num
            _num3State.value = true
        } else if (_num1.value != -1) {
            _num2.value = num
            _num2State.value = true
        } else {
            _num1.value = num
            _num1State.value = true
        }
    }

    fun deleteNumber() {
        if (_num3.value != -1) {
            _num3.value = -1
            _num3StateDelete.value = true
        } else if (_num2.value != -1) {
            _num2.value = -1
            _num2StateDelete.value = true
        } else if (_num1.value != -1) {
            _num1.value = -1
            _num1StateDelete.value = true
        }
    }

    fun setPreference() {
        val pwd =
            _num1.value.toString() +
                    _num2.value.toString() +
                    _num3.value.toString() +
                    _num4.value.toString()
        Application.lockNumber.addNumber(pwd)
        Application.lockNumber.addType(PWD_TYPE.CHECK_LOCK)
        _changeText.value = "비밀번호를 재입력하세요."
    }

    fun checkPreference() {
        val pwd =
            _num1.value.toString() +
                    _num2.value.toString() +
                    _num3.value.toString() +
                    _num4.value.toString()
        if (Application.lockNumber.getAddNumber().equals(pwd)) {
            if (Application.lockNumber.getAddType() == PWD_TYPE.CHANGE_LOCK) {
                _check.value = true
                Application.lockNumber.addType(PWD_TYPE.ENABLE_LOCK)
            } else if (Application.lockNumber.getAddType() == PWD_TYPE.CHECK_LOCK_MAIN) {
                _checkMain.value = true
            } else {
                _setAll.value = true
            }
        } else {
            _changeTextError.value = "다시 입력해주세요."
        }
    }

    fun reset(){
        _num1.value = -1
        _num2.value = -1
        _num3.value = -1
        _num4.value = -1
    }
}