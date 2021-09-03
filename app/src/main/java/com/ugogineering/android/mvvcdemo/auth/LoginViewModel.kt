package com.ugogineering.android.mvvcdemo.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ugogineering.android.mvvcdemo.data.model.LoginBody
import com.ugogineering.android.mvvcdemo.data.model.LoginResponse
import com.ugogineering.android.mvvcdemo.network.TestApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String>
        get() = _phone

    // Login Response
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse
    // Login Status
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean>
        get() = _loginStatus
    // Login Message
    private val _loginMessage = MutableLiveData<String>()
    val loginMessage: LiveData<String>
        get() = _loginMessage

    // User Token
    private val _userToken = MutableLiveData<String>()
    val userToken: LiveData<String>
        get() = _userToken

    init {
        _loginMessage.value = "Enter your details to login"
        _loginStatus.value = false
    }

    // Adding a coroutine job
    private var viewModelJob = Job()
    // Creating a coroutine scope for the new job using the main dispatcher
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    // Sets the value of the loginResponse LiveData to the Login API login response
    fun login(loginBody: LoginBody) {
        coroutineScope.launch {
            val loginDeferred = TestApi.retrofitService.login(loginBody)
            try {
                val loginResult = loginDeferred.await()
                _loginMessage.value = "Success: ${loginResult.message}"
                _loginResponse.value = loginResult
                _userToken.value = loginResult.data.token
                _loginStatus.value = loginResult.success
            } catch (e: Exception) {
                _loginMessage.value = "Failure: ${e.message}"
            }
        }
    }

    fun goToMainActivityCompleteTwo() {
        _loginStatus.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}