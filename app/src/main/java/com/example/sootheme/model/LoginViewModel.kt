package com.example.sootheme.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sootheme.data.UserRepository
import com.example.sootheme.network.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repo: UserRepository) : ViewModel() {

    private var _error = MutableLiveData<Boolean?>()
    val error: LiveData<Boolean?> = _error

    private var _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    /*fun userLogin(email: String, password: String) {
        val client = repo.userLogin(email, password)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.loginResult
                    _error.value = false
                    _user.postValue(responseBody!!)
                } else {
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _error.value = true
            }
        })
    }*/
}