package com.example.sootheme.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicData(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("content")
    val content: String?,

    @field:SerializedName("cover")
    val cover: String?,

) : Parcelable
