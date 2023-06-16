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

class DetailStoryViewModel(private val repo: UserRepository) : ViewModel() {

    val detailStory = MutableLiveData<StoryData>()

    private var _error = MutableLiveData<Boolean?>()
    val error: LiveData<Boolean?> = _error

    fun detailStory(id: Int) {
        repo.getStory()
            .getDetailStory(id)
            .enqueue(object : Callback<StoryData> {
                override fun onResponse(
                    call: Call<StoryData>,
                    response: Response<StoryData>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        detailStory.postValue(responseBody!!)
                        Log.e("DetailStory", "Success")
                        _error.value = false
                    }
                }

                override fun onFailure(call: Call<StoryData>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getDetailStory(): LiveData<StoryData> {
        return detailStory
    }

}