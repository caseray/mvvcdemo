package com.ugogineering.android.mvvcdemo.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ugogineering.android.mvvcdemo.BuildConfig
import com.ugogineering.android.mvvcdemo.data.model.SignupBody
import com.ugogineering.android.mvvcdemo.data.model.SignupResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "https://caseraycloud.com.ng/testapi/testapi/public/"

// Creating Moshi instance
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Creating a Retrofit object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(
        OkHttpClient.Builder().also { client ->
            if(BuildConfig.DEBUG){
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }
        }.build()
    )
    .build()


interface TestApiService {
    @Headers("Content-Type: application/json")
    @POST("api/auth/register")
    fun signUp(@Body signupBody: SignupBody):
            Deferred<SignupResponse>
}

object TestApi {
    val retrofitService: TestApiService by lazy {
        retrofit.create(TestApiService::class.java)
    }
}