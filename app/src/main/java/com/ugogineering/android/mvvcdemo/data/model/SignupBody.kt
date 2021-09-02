package com.ugogineering.android.mvvcdemo.data.model

data class SignupBody(
    val name: String, val email: String,
    val password: String, val c_password: String,
    val lname: String, val phone_no: String
)
