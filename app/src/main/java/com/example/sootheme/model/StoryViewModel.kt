package com.example.sootheme.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sootheme.data.StoryData
import com.example.sootheme.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel(private val repo: UserRepository) : ViewModel() {

    private var _user = MutableLiveData<ArrayList<StoryData>>()
    val user: LiveData<ArrayList<StoryData>> = _user

    fun getStoryTime() {
        val client = repo.storyTime()
        client.enqueue(object : Callback<ArrayList<StoryData>> {
            override fun onResponse(
                call: Call<ArrayList<StoryData>>,
                response: Response<ArrayList<StoryData>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.e("StoryViewModel", "$responseBody")
                    _user.postValue(responseBody!!)
                } else {
                    Log.e("StoryViewModel", "response is not successful")
                }
            }

            override fun onFailure(call: Call<ArrayList<StoryData>>, t: Throwable) {
                Log.e("StoryViewModel", "API call failed: ${t.message}")
            }
        })
    }

}