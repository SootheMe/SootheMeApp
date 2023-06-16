package com.example.sootheme.network

import com.example.sootheme.data.ChatResponse
import com.example.sootheme.data.MusicData
import com.example.sootheme.data.StoryData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("login")
    fun userLogin(
        @Body user: Map<String, String>
    ): Call<UserResponse>

    @POST("register")
    fun userRegister(
        @Body user: Map<String, String>
    ): Call<UserResponse>

    @GET("username")
    fun userName(
    ): Call<UserResponse>

    @GET("stories")
    fun getStory(
    ): Call<ArrayList<StoryData>>

    @GET("stories/{id}")
    fun getDetailStory(
        @Path("id") id: Int
    ): Call<StoryData>

    @POST("predict_text")
    fun botChat(
        @Body text: RequestBody
    ): Call<ChatResponse>

    @GET("music")
    fun getMusic(
    ): Call<ArrayList<MusicData>>

    @GET("music/{id}")
    fun getDetailMusic(
        @Path("id") id: Int
    ): Call<MusicData>
}