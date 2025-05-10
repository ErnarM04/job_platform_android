package com.example.startups.models

import com.google.gson.annotations.SerializedName

data class Application(
    val id: Int,
    val vacancy: Int,
    val status: String,
    @SerializedName("cover_letter") val letter: String
)
