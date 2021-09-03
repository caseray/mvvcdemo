package com.ugogineering.android.mvvcdemo.data.model

data class LoginResponse(
    val success: Boolean, val data: LoginData,
    val message: String
) {
    data class LoginData(
        val token: String,
        val name: String
    )
}
