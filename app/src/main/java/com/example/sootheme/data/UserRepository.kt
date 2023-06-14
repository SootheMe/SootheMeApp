package com.example.sootheme.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.sootheme.data.datastore.SettingPref
import com.example.sootheme.network.ApiInterceptor
import com.example.sootheme.network.ApiService
import com.example.sootheme.network.UserResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    fun getFirstTime(): LiveData<Boolean> = pref.getFirstTime().asLiveData()

    suspend fun login() = pref.login()

    suspend fun logout() = pref.logout()

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

    private fun userNameLogin(token: String): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor(token))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://app1-dot-sootheme-388911.et.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun getStory(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://app2-dot-sootheme-388911.et.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun appUserName(
        token: String
    ): Call<UserResponse> {
        return userNameLogin(token).userName()
    }

    fun storyTime(): Call<ArrayList<StoryData>>{
        return getStory().getStory()
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