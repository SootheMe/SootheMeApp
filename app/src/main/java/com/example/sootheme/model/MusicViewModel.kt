package com.example.sootheme.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sootheme.data.MusicData
import com.example.sootheme.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicViewModel(private val repo: UserRepository) : ViewModel() {

    private var _user = MutableLiveData<ArrayList<MusicData>>()
    val user: LiveData<ArrayList<MusicData>> = _user

    fun getMusicTime() {
        val client = repo.musicTime()
        client.enqueue(object : Callback<ArrayList<MusicData>> {
            override fun onResponse(
                call: Call<ArrayList<MusicData>>,
                response: Response<ArrayList<MusicData>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.e("MusicViewModel", "$responseBody")
                    _user.postValue(responseBody!!)
                } else {
                    Log.e("MusicViewModel", "response is not successful")
                }
            }

            override fun onFailure(call: Call<ArrayList<MusicData>>, t: Throwable) {
                Log.e("MusicViewModel", "API call failed: ${t.message}")
            }
        })
    }
}