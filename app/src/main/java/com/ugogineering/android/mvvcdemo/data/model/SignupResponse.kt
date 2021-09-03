package com.ugogineering.android.mvvcdemo.data.model

data class SignupResponse(
    val success: Boolean, val data: SignupData,
    val message: String
) {
    data class SignupData(
        val token: String,
        val name: String
    )
}
