package com.ugogineering.android.mvvcdemo.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {
    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String>
        get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String>
        get() = _lastName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String>
        get() = _phone

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private val _passwordAgain = MutableLiveData<String>()
    val passwordAgain: LiveData<String>
        get() = _passwordAgain

    private val _eventGoToSignUpReportFragment = MutableLiveData<Boolean>()
    val eventGoToSignUpReportFragment: LiveData<Boolean>
        get() = _eventGoToSignUpReportFragment

    private val _eventGoToLoginFragment = MutableLiveData<Boolean>()
    val eventGoToLoginFragment: LiveData<Boolean>
        get() = _eventGoToLoginFragment

    init {
        _eventGoToSignUpReportFragment.value = false
        _eventGoToLoginFragment.value = false
    }

    fun processInput(firstName: String, lastName: String, email: String, phone: String, password: String ) {
        _firstName.value = firstName
        _lastName.value = lastName
        _email.value = email
        _phone.value = phone
        _password.value = password
    }
    fun processLoginInput(email: String, password: String) {
        _email.value = email
        _password.value = password
    }

    fun goToSignUpReportFragment() {
        _eventGoToSignUpReportFragment.value = true
    }
    fun goToSignUpReportFragmentComplete() {
        _eventGoToSignUpReportFragment.value = false
    }
    fun goToLoginReportFragment() {
        _eventGoToLoginFragment.value = true
    }
    fun goToLoginReportFragmentComplete() {
        _eventGoToLoginFragment.value = false
    }

}