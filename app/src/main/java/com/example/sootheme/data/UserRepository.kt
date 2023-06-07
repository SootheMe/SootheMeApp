package com.example.sootheme.data

import com.example.sootheme.network.ApiService
import com.example.sootheme.network.UserResponse
import retrofit2.Call

class UserRepository(private val apiService: ApiService) {
    fun userLogin(email: String, password: String): Call<UserResponse> {
        val user: Map<String, String> = mapOf(
            "email" to email,
            "password" to password
        )
        return apiService.userLogin(user)
    }

    fun userRegister(name: String, email: String, password: String): Call<UserResponse> {
        val user: Map<String, String> = mapOf(
            "name" to name,
            "email" to email,
            "password" to password
        )
        return apiService.userRegister(user)
    }
}