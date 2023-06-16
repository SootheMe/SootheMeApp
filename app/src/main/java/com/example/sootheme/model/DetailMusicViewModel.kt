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

class DetailMusicViewModel(private val repo: UserRepository) : ViewModel() {

    val detailMusics = MutableLiveData<MusicData>()

    private var _error = MutableLiveData<Boolean?>()
    val error: LiveData<Boolean?> = _error

    fun detailMusic(id: Int) {
        repo.getMusic()
            .getDetailMusic(id)
            .enqueue(object : Callback<MusicData> {
                override fun onResponse(
                    call: Call<MusicData>,
                    response: Response<MusicData>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        detailMusics.postValue(responseBody!!)
                        Log.e("DetailStory", "Success")
                        _error.value = false
                    }
                }

                override fun onFailure(call: Call<MusicData>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getDetailStory(): LiveData<MusicData> {
        return detailMusics
    }
}