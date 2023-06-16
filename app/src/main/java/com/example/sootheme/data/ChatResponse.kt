package com.example.sootheme.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatResponse(

    @field:SerializedName("you")
    val you: String? = null,

    @field:SerializedName("bot")
    val bot: String?,

    @field:SerializedName("next_patterns")
    val next_patterns: List<String>? = null,

    ) : Parcelable
