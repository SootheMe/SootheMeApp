package com.example.sootheme.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.sootheme.data.datastore.SettingPref
import com.example.sootheme.network.ApiInterceptor
import com.example.sootheme.network.ApiService
import com.example.sootheme.network.UserResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository(
    private val apiService: ApiService,
    private val pref: SettingPref
) {

    fun getUserToken(): LiveData<String> = pref.getUserToken().asLiveData()

    suspend fun saveUserToken(value: String) = pref.saveUserToken(value)

    fun getUserName(): LiveData<String> = pref.getUserName().asLiveData()

    suspend fun saveUserName(value: String) = pref.saveUserName(value)

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

    fun userLogin(token: String): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor(token))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sootheme-388911.et.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun appUserName(
        token: String
    ): Call<UserResponse> = userLogin(token).userName()

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