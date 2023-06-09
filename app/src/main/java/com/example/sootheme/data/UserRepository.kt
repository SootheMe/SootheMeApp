package com.example.sootheme.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.sootheme.data.datastore.SettingPref
import com.example.sootheme.network.ApiService
import com.example.sootheme.network.UserResponse
import retrofit2.Call

class UserRepository(
    private val apiService: ApiService,
    private val pref: SettingPref
) {

    fun getUserToken(): LiveData<String> = pref.getUserToken().asLiveData()

    suspend fun saveUserToken(value: String) = pref.saveUserToken(value)
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

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            pref: SettingPref
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref).also { instance = it }
            }
    }
}