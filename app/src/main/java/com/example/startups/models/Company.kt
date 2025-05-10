package com.example.startups.models

import com.google.gson.annotations.SerializedName

data class Company(
    val id: Int,
    val name: String,
    val description: String,
    val industry: String,
    val website: String,
    val logo: String,
    @SerializedName("founded_year") val foundedYear: String
)
