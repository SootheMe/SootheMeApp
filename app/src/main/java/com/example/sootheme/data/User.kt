package com.example.sootheme.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @field:SerializedName("accessToken")
    val token: String,
) : Parcelable
