package com.example.sootheme.network

import com.example.sootheme.data.StoryData
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("msg")
    val message: String? = null,

    @field:SerializedName("accessToken")
    val accessToken: String? = null,

    @field:SerializedName("userName")
    val userName: String? = null,

    val storyTime: ArrayList<StoryData>

)
