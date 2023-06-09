package com.example.sootheme.network

import com.example.sootheme.data.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("msg")
    val message: String? = null,

    @field:SerializedName("loginResult")
    val loginResult: User? = null,
)
