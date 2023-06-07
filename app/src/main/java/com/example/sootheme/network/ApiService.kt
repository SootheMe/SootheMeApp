package com.example.sootheme.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun userLogin(
        @Body user: Map<String, String>
    ): Call<UserResponse>

    @POST("register")
    fun userRegister(
        @Body user: Map<String, String>
    ): Call<UserResponse>
}