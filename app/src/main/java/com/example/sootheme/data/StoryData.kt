package com.example.sootheme.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryData(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("author")
    val author: String?,

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("content")
    val content: String?,

    @field:SerializedName("cover")
    val cover: String?,

) : Parcelable
