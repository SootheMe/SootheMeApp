package com.example.sootheme.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sootheme.data.UserRepository
import com.example.sootheme.network.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repo: UserRepository) : ViewModel() {

    private var _user = MutableLiveData<String?>()
    val user: LiveData<String?> = _user

    fun getUserToken() = repo.getUserToken()

    fun getUser(
        token: String
    ) {
        val client = repo.appUserName(token)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.userName
                    Log.e("MainViewModel", "$responseBody")
                    if (responseBody != null) {
                        _user.postValue(responseBody)
                    } else {
                        Log.e("MainViewModel", "responseBody is null")
                    }
                } else {
                    Log.e("MainViewModel", "response is not successful")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("MainViewModel", "API call failed: ${t.message}")
            }
        })
    }

    fun saveUserName(name: String?) {
        viewModelScope.launch {
            if (name != null) {
                repo.saveUserName(name)
            }
        }
    }

    fun getUserName() = repo.getUserName()

    fun userLogoutApp() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}