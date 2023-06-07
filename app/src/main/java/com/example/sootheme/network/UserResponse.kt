package com.example.sootheme.network

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("msg")
    val message: String? = null,
)
