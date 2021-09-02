package com.ugogineering.android.mvvcdemo.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ugogineering.android.mvvcdemo.data.model.SignupBody
import com.ugogineering.android.mvvcdemo.data.model.SignupResponse
import com.ugogineering.android.mvvcdemo.network.TestApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    // The internal MutableLiveData String that stores the signup response
    private val _signupResponse = MutableLiveData<SignupResponse>()
    // The external immutable LiveData for the response String
    val signupResponse: LiveData<SignupResponse>
        get() = _signupResponse

    // Signup Status
    private val _signupStatus = MutableLiveData<Boolean>()
    val signupStatus: LiveData<Boolean>
        get() = _signupStatus

    // Signup Message
    private val _signupMessage = MutableLiveData<String>()
    val signupMessage: LiveData<String>
        get() = _signupMessage

    // User Token
    private val _userToken = MutableLiveData<String>()
    val userToken: LiveData<String>
        get() = _userToken



    private val _eventGoToSignUpReportFragment = MutableLiveData<Boolean>()
    val eventGoToSignUpReportFragment: LiveData<Boolean>
        get() = _eventGoToSignUpReportFragment

    private val _eventGoToLoginFragment = MutableLiveData<Boolean>()
    val eventGoToLoginFragment: LiveData<Boolean>
        get() = _eventGoToLoginFragment
    init {
        _eventGoToSignUpReportFragment.value = false
        _eventGoToLoginFragment.value = false
        _signupMessage.value = "Default signup message"
        _signupStatus.value = false
    }
    // Adding a coroutine job
    private var viewModelJob = Job()
    // Creating a coroutine scope for the new job using the main dispatcher
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )
    // Sets the value of the signupResponse LiveData to the Login API signup response
    fun signUp(signupBody: SignupBody) {
        coroutineScope.launch {
            val signUpDeferred = TestApi.retrofitService.signUp(signupBody)
            try {
                val signUpResult = signUpDeferred.await()
                _signupMessage.value = "Success: ${signUpResult.message} "
                _signupResponse.value = signUpResult
                _userToken.value = signUpResult.data.token
                _signupStatus.value = signUpResult.success
                // Trigger navigation to SignUpReportFragment
                //goToSignUpReportFragment()
                // _eventGoToSignUpReportFragment.value = signUpResult.success
            } catch (e: Exception) {
                _signupMessage.value = "Failure: ${e.message}"
                // Trigger navigation to SignUpReportFragment
               // goToSignUpReportFragment()
            }
        }
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
    fun goToMainActivityComplete() {
        _signupStatus.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}