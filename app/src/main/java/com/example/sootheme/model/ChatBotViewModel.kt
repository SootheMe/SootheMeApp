package com.example.sootheme.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sootheme.data.ChatResponse
import com.example.sootheme.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBotViewModel(private val repo: UserRepository) : ViewModel() {

    val botResponse = MutableLiveData<ChatResponse>()

    fun chatBot(
        text: String
    ) {
        val client = repo.getBotResponse(text)
        client.enqueue(object : Callback<ChatResponse> {
            override fun onResponse(
                call: Call<ChatResponse>,
                response: Response<ChatResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    botResponse.postValue(responseBody!!)
                    Log.e("ChatBot", "Success")
                    //_error.value = false
                } else {
                    Log.e("ChatBot", "$response")
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                //_error.value = true
                Log.e("Failure", t.message.toString())
            }
        })
    }

    fun getBotResponse(): LiveData<ChatResponse> {
        return botResponse
    }

    fun delData() {
        botResponse.postValue(ChatResponse("", ""))
    }
}